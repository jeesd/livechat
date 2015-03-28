package org.mylivedata.app.connection.integration.router;

import org.mylivedata.app.dashboard.domain.custom.SecureUser;
import org.mylivedata.app.webchat.domain.ChatUser;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.messaging.Message;

/**
 * Created by lubo08 on 10.12.2014.
 */
@MessageEndpoint
public class SessionLogTypeRouter {

    //@Router(inputChannel = "logSession", poller = @Poller("myPoller"))
    public String routeChatOrNotification(Message<?> message){
        Object payload = message.getPayload();

        if(payload instanceof ChatUser){
            return "chatUser";
        }else if (payload instanceof SecureUser) {
            return "newUserNotification";
        }
        return "chatUser";
    }

}
