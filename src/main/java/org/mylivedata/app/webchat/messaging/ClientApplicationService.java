
package org.mylivedata.app.webchat.messaging;

import org.cometd.annotation.Listener;
import org.cometd.annotation.Service;
import org.cometd.annotation.Session;
import org.cometd.bayeux.client.ClientSessionChannel;
import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ServerChannel;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;
import org.mylivedata.app.connection.MessageType;
import org.mylivedata.app.connection.domain.VisitorPrincipal;
import org.mylivedata.app.dashboard.messaging.DashboardService;
import org.mylivedata.app.util.SessionUtils;
import org.mylivedata.app.webchat.domain.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Named
@Singleton
@Service("clientService")
public class ClientApplicationService
{
    @Inject
    private BayeuxServer bayeux;
    @Session
    private ServerSession serverSession;
    @Autowired
    private DashboardService dashboardService;
    @Autowired
    private SessionUtils sessionUtils;

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

    @Listener("/client/request")
    public void processClientRequest(ServerSession remote, ServerMessage.Mutable message)
    {
        VisitorPrincipal user = (VisitorPrincipal)remote.getAttribute("user");
        Map<String, Object> messageResp = new HashMap<String, Object>();
        messageResp.put("type", MessageType.VISITOR_CHAT_REQUEST);
        messageResp.put("id", UUID.randomUUID().toString());
        messageResp.put("userID", user.getSecureUser().getId());
        messageResp.put("userName", user.getSecureUser().getFirstName());
        messageResp.put("data", user.getSecureUser().getFirstName());

        sessionUtils.changeUserStatus(user.getSecureUser(),UserStatus.REQUEST_CHAT);

        dashboardService.sendMessageToChannel("/visitor/"+user.getSecureUser().getAccountIdentity(),messageResp);


    }

}
