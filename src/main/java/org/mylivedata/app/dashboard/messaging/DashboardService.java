
package org.mylivedata.app.dashboard.messaging;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.cometd.annotation.Configure;
import org.cometd.annotation.Listener;
import org.cometd.annotation.Service;
import org.cometd.annotation.Session;
import org.cometd.bayeux.client.ClientSessionChannel;
import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ConfigurableServerChannel;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;
import org.cometd.server.filter.DataFilter;
import org.cometd.server.filter.DataFilterMessageListener;
import org.cometd.server.filter.JSONDataFilter;
import org.cometd.server.filter.NoMarkupFilter;
import org.mylivedata.app.dashboard.messaging.security.DashboardUserAuthorizer;
import org.mylivedata.app.webchat.domain.ChatUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@Singleton
@Service("dashboardService")
public class DashboardService
{
    private final Logger LOGGER = LoggerFactory.getLogger(DashboardService.class);

    @Inject
    private BayeuxServer bayeux;
    @Session
    private ServerSession serverSession;

    @PostConstruct
    public void init()
    {
    }

    public void sendMessageToChannel(String channelName, Object message) {
        ClientSessionChannel channel = serverSession.getLocalSession().getChannel(channelName);
        channel.publish(message);
    }

    public void timOutMessage(ChatUser user){
        try {
            if(user != null && user.isOperator()){
                List<String> sessionIds = user.getChatSessionIds();
                for(String sessionId: sessionIds)
                {
                        ServerSession peer = bayeux.getSession(sessionId);
                        peer.disconnect();//deliver(serverSession,forward);
                }
            }
        }catch (Exception e){
            LOGGER.debug("User "+user.getIdentificationHash()+": nothing to disconnect");
        }
    }

    @Configure({"/visitor/**","/user/**"})
    protected void configureChatStarStar(ConfigurableServerChannel channel)
    {
        DataFilterMessageListener noMarkup = new DataFilterMessageListener(new NoMarkupFilter(),new BadWordFilter());
        channel.addListener(noMarkup);
        channel.addAuthorizer(DashboardUserAuthorizer.GRANT_ALL);

    }

    @Listener("/dashboard/message")
    public void processAppMessage(ServerSession remote, ServerMessage.Mutable message)
    {
        Map<String, Object> input = message.getDataAsMap();
        String name = (String)input.get("name");

        Map<String, Object> output = new HashMap<>();
        output.put("greeting", "Hello, " + name);
        //remote.deliver(serverSession, "/hello", output, null);

    }

    class BadWordFilter extends JSONDataFilter
    {
        @Override
        protected Object filterString(String string)
        {
            if (string.indexOf("dang")>=0)
                throw new DataFilter.Abort();
            return string;
        }
    }

}
