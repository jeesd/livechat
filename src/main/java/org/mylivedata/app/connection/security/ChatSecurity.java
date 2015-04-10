package org.mylivedata.app.connection.security;

import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;
import org.cometd.server.DefaultSecurityPolicy;
import org.cometd.websocket.server.AbstractWebSocketTransport;
import org.mylivedata.app.dashboard.domain.custom.SecureUser;
import org.springframework.security.core.context.SecurityContext;

import java.util.Map;

/**
 * Created by lubo08 on 8.4.2015.
 */
public class ChatSecurity extends DefaultSecurityPolicy {

    @Override
    public boolean canHandshake(BayeuxServer server, ServerSession session, ServerMessage message) {

        if (session.isLocalSession())
            return true;

        SecurityContext securityContext = (SecurityContext) server.getContext().getHttpSessionAttribute("SPRING_SECURITY_CONTEXT");

        if (securityContext != null && securityContext.getAuthentication().isAuthenticated()) {
            SecureUser secUser = (SecureUser) securityContext.getAuthentication().getPrincipal();
            if(secUser != null && secUser.isUserInRole("ROLE_USER")){
                // will validate if user have already active session if transport is not websocket.
                if(!server.getCurrentTransport().getName().equals(AbstractWebSocketTransport.NAME)){

                }
                session.setAttribute("secureUser", secUser);
                return true;
            } else if (secUser != null && secUser.isUserInRole("ROLE_VISITOR")) {
                return false;
            }
        } else {

        }


        Map<String, Object> ext = message.getExt();




        return false;
    }
}
