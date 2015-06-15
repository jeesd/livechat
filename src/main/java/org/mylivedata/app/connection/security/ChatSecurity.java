package org.mylivedata.app.connection.security;

import org.cometd.bayeux.Message;
import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;
import org.cometd.server.DefaultSecurityPolicy;
import org.cometd.websocket.server.AbstractWebSocketTransport;
import org.mylivedata.app.connection.domain.VisitorPrincipal;
import org.mylivedata.app.connection.integration.ConnectionManagerGateway;
import org.mylivedata.app.connection.integration.SessionGateway;
import org.mylivedata.app.dashboard.domain.custom.SecureUser;
import org.mylivedata.app.dashboard.repository.service.AccountService;
import org.mylivedata.app.dashboard.repository.service.UserService;
import org.mylivedata.app.util.SessionUtils;
import org.mylivedata.app.webchat.domain.ChatUser;
import org.mylivedata.app.webchat.domain.UserStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by lubo08 on 8.4.2015.
 */
public class ChatSecurity extends DefaultSecurityPolicy {

    private static Logger logger = LoggerFactory.getLogger(ChatSecurity.class);

    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private SessionUtils sessionUtils;
    @Autowired
    private ConnectionManagerGateway connectionManagerGateway;

    @Override
    public boolean canHandshake(BayeuxServer server, ServerSession session, ServerMessage message) {

        if (session.isLocalSession())
            return true;

        Map<String, Object> ext = message.getExt();
        ServerMessage.Mutable handshakeReply = message.getAssociated();
        SecurityContext securityContext = (SecurityContext) server.getContext().getHttpSessionAttribute("SPRING_SECURITY_CONTEXT");

        //if user is not authorized by spring security module then validate and create anonymous user for website chat
       if (securityContext != null && securityContext.getAuthentication().isAuthenticated()) {
            SecureUser secUser = (SecureUser) securityContext.getAuthentication().getPrincipal();
            if(secUser != null && secUser.isUserInRole("ROLE_USER")){
                // will validate if user have already active session if transport is not websocket.
                // if true then will be allowed only one direct connection for session
                if(!server.getCurrentTransport().getName().equals(AbstractWebSocketTransport.NAME)){

                }
                session.setAttribute("user", createUser(server, secUser));
                session.setAttribute("browserId", server.getContext().getHttpSessionId());
                handshakeReply.getExt(true).put("accountId", secUser.getAccountIdentity());
                logger.debug("Allowed chat for authorized dashboard user: "+secUser.getEmail());
                return true;
            } else if (secUser != null && secUser.isUserInRole("ROLE_VISITOR")) {

                session.setAttribute("browserId", server.getContext().getHttpSessionId());
                logger.debug("Allowed chat for authorized website chat user: "+secUser.getEmail());
                return false;
            }
        } else {

            Map<String, Object> clientData = (Map<String, Object>)ext.get("client");
            String accountID = null;
            String browserId = null;

            if(clientData != null && !clientData.isEmpty()){
                accountID = (String)clientData.get("accountID");
                browserId = (String)clientData.get("BID");
            }
            if(browserId == null){
                browserId = UUID.randomUUID().toString();
            }

            String transportName = server.getCurrentTransport().getName();

            if(sessionUtils.getBrowserConnectionsManager(browserId) != null && !connectionManagerGateway.allowDirectConnectionOrSwitchToClient(
                            sessionUtils.getBrowserConnectionsManager(browserId))
            )
            {
                return notAvailableConnections(session, message);
            }

            // validate if anonymous user access is allowed if origin url is allowed for account id.
            if(accountID == null && !isAllowedAnonymousUserAccess(server.getContext().getHeader("Origin"), accountID))
                return notAuthorized(session,message);
            //create anonymous user
            VisitorPrincipal user = createAnonymousUser(server, clientData, browserId, session.getId());
            session.setAttribute("user", user);
            session.setAttribute("transportName", transportName);
            session.setAttribute("browserId", browserId);
            // set reply info for browser id

            handshakeReply.getExt(true).put("BID", browserId);
            handshakeReply.getExt(true).put("accountId", user.getAccountIdHash());
            handshakeReply.getExt(true).put("userName", user.getSecureUser().getUsername());

            logger.debug("Allowed chat for anonymous website chat user: ");
            return true;
        }

        logger.info("Blocked chat access is unusual check if too much happening");
        return notAuthorized(session,message);
    }

    private VisitorPrincipal createUser(BayeuxServer server, SecureUser secUser) {
        VisitorPrincipal visitorPrincipal = new VisitorPrincipal();
        visitorPrincipal.setSecureUser(secUser);
        visitorPrincipal.setOrigin(server.getContext().getHeader("Origin"));
        visitorPrincipal.setReferrer(server.getContext().getHeader("Referer"));
        visitorPrincipal.setRemoteAddress(server.getContext().getRemoteAddress().getHostString());
        visitorPrincipal.setUserHashId((String)server.getContext().getHttpSessionAttribute("userIdentityHash"));
        //addUserIntoCache(visitorPrincipal,server);
        return visitorPrincipal;
    }

    private VisitorPrincipal createAnonymousUser(BayeuxServer server, Map<String, Object> clientData, String browserId, String sessionID) {
        VisitorPrincipal visitorPrincipal = new VisitorPrincipal();
        visitorPrincipal.setOrigin(server.getContext().getHeader("Origin"));
        visitorPrincipal.setReferrer(server.getContext().getHeader("Referer"));
        visitorPrincipal.setRemoteAddress(server.getContext().getRemoteAddress().getHostString());
        visitorPrincipal.setAccountIdHash((String)clientData.get("accountID"));
        visitorPrincipal.setScreenWH((String)clientData.get("wh"));
        visitorPrincipal.setSecureUser(userService.getSecureUserByHash(visitorPrincipal));
        //addUserIntoCache(visitorPrincipal,server);
        return visitorPrincipal;
    }

    private void addUserIntoCache (VisitorPrincipal principal, BayeuxServer server) {
        //ChatUser result = sessionGateway.addSession(principal.getSecureUser(), principal.getRemoteAddress(), new Date().getTime(), server.getContext().getHeader("User-Agent"), principal.getDepartment(),sessionID);
        //server.getContext().setHttpSessionAttribute("sessionId", new Long(result.getSessionId()).intValue());
    }

    private boolean isAllowedAnonymousUserAccess(String origin, String accountIdentity) {
        try {
            if(!accountService.checkAccountIdVsUrl(origin,accountIdentity))
                return false;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return true;
    }

    private boolean notAuthorized(ServerSession session, ServerMessage message){
        // Retrieve the handshake response
        ServerMessage.Mutable handshakeReply = message.getAssociated();

        // Modify the advice, in this case tell to try again
        // If the advice is not modified it will default to disconnect the client
        Map advice = handshakeReply.getAdvice(true);
        advice.put(Message.RECONNECT_FIELD, Message.RECONNECT_HANDSHAKE_VALUE);
        Map authentication = new HashMap();
        // Modify the ext field with extra information on the authentication failure
        Map ext = handshakeReply.getExt(true);

        ext.put("auth", authentication);
        authentication.put("failureReason", "invalid_credentials");
        return false;
    }

    private boolean notAvailableConnections(ServerSession session, ServerMessage message){
        // Retrieve the handshake response
        ServerMessage.Mutable handshakeReply = message.getAssociated();

        // Modify the advice, in this case tell to try again
        // If the advice is not modified it will default to disconnect the client
        Map advice = handshakeReply.getAdvice(true);
        advice.put(Message.RECONNECT_FIELD, Message.RECONNECT_HANDSHAKE_VALUE);
        Map authentication = new HashMap();
        // Modify the ext field with extra information on the authentication failure
        Map ext = handshakeReply.getExt(true);

        ext.put("auth", authentication);
        authentication.put("failureReason", "not_available_connections");
        return false;
    }

}
