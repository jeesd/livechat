package org.mylivedata.app.connection.session;

import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;
import org.cometd.server.ServerSessionImpl;
import org.mylivedata.app.connection.domain.VisitorPrincipal;
import org.mylivedata.app.connection.integration.SessionGateway;
import org.mylivedata.app.util.SessionUtils;
import org.mylivedata.app.webchat.domain.ChatUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

/**
 * Created by lubo08 on 9.4.2015.
 */
public class ChatSessionListener implements BayeuxServer.SessionListener {

    private static Logger logger = LoggerFactory.getLogger(ChatSessionListener.class);

    @Autowired
    private SessionGateway sessionGateway;
    @Autowired
    private SessionUtils sessionUtils;

    @Override
    public void sessionAdded(ServerSession session, ServerMessage message) {
        VisitorPrincipal user = (VisitorPrincipal)session.getAttribute("user");
        if(user != null) {
            String transportName = (String) session.getAttribute("transportName");
            String accountID = user.getAccountIdHash();
            String browserId = (String) session.getAttribute("browserId");
            //register chat session per browser id into ehcache entity.
            sessionUtils.addChatSession(browserId,session.getId(),transportName,user.getUserHashId());
            // log session into db and user into cache.
            ChatUser result = sessionGateway.addSession(user.getSecureUser(), user.getRemoteAddress(), new Date().getTime(), session.getUserAgent(), user.getDepartment(), session.getId());
            //server.getContext().setHttpSessionAttribute("sessionId", new Long(result.getSessionId()).intValue());

            logger.debug("Session for user ["+user.getSecureUser().getEmail()+"] added into cache.");
        }
    }

    @Override
    public void sessionRemoved(ServerSession session, boolean timedOut) {
        sessionUtils.removeChatSession((String) session.getAttribute("browserId"),session.getId());
        sessionGateway.removeSession(((VisitorPrincipal)session.getAttribute("user")).getSecureUser(),session.getId());
        logger.debug("Session for user ["+((VisitorPrincipal)session.getAttribute("user")).getSecureUser().getEmail()+"] removed from cache.");
    }
}
