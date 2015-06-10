
package org.mylivedata.app.webchat.messaging;

import org.cometd.annotation.Listener;
import org.cometd.annotation.Service;
import org.cometd.annotation.Session;
import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Named
@Singleton
@Service("clientService")
public class ClientApplicationService
{
    @Inject
    private BayeuxServer bayeux;
    @Session
    private ServerSession serverSession;

    @PostConstruct
    public void init()
    {
    }

    @Listener("/client/message")
    public void processAppMessage(ServerSession remote, ServerMessage.Mutable message)
    {
        Map<String, Object> input = message.getDataAsMap();
        String name = (String)input.get("name");

        Map<String, Object> output = new HashMap<>();
        output.put("greeting", "Hello, " + name);
        //remote.deliver(serverSession, "/hello", output, null);

    }

    @Listener("/client/info")
    public void processClientInfo(ServerSession remote, ServerMessage.Mutable message)
    {
        Map<String, Object> input = message.getDataAsMap();
        String name = (String)input.get("department");

    }

}
