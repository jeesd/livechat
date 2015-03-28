package org.mylivedata.app.connection.integration.router;

import org.cometd.websocket.server.AbstractWebSocketTransport;
import org.mylivedata.app.connection.BrowserConnectionManager;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Router;
import org.springframework.messaging.Message;

/**
 * Created by lubo08 on 13.11.2014.
 */
@MessageEndpoint
public class ManageConnectionRouter {

    @Router(inputChannel = "routeConnection")
    public String route(Message<?> message){
        BrowserConnectionManager browserConnectionManager = (BrowserConnectionManager)message.getPayload();
        if(AbstractWebSocketTransport.NAME.equals(browserConnectionManager.getConnectionType())){
            return "manageWebsocketConnection";
        } else {
            return "manageCallbackPoolingConnection";
        }
    }
}
