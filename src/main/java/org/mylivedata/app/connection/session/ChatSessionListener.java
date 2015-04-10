package org.mylivedata.app.connection.session;

import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;
import org.mylivedata.app.connection.integration.SessionGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by lubo08 on 9.4.2015.
 */
public class ChatSessionListener implements BayeuxServer.SessionListener {

    private static Logger logger = LoggerFactory.getLogger(ChatSessionListener.class);

    @Autowired
    private SessionGateway sessionGateway;

    @Override
    public void sessionAdded(ServerSession session, ServerMessage message) {

        //sessionGateway.addSession();
    }

    @Override
    public void sessionRemoved(ServerSession session, boolean timedout) {

    }
}
