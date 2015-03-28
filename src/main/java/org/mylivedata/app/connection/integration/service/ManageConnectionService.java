package org.mylivedata.app.connection.integration.service;

import org.mylivedata.app.connection.AsyncConnectionManager;
import org.mylivedata.app.connection.BrowserConnectionManager;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

/**
 * Created by lubo08 on 13.11.2014.
 */
@MessageEndpoint
public class ManageConnectionService {


    private AsyncConnectionManager asyncConnectionManager = new AsyncConnectionManager();

    @ServiceActivator(inputChannel = "manageWebsocketConnection", outputChannel = "canConnect")
    public Message canDoWebsocketConnection(Message message){
        BrowserConnectionManager browserConnectionManager = (BrowserConnectionManager)message.getPayload();
        Boolean canConnect = asyncConnectionManager.canConnect(browserConnectionManager);
        return MessageBuilder.fromMessage(message).withPayload(canConnect).build();
    }

    @ServiceActivator(inputChannel = "manageCallbackPoolingConnection", outputChannel = "canConnect")
    public Message canDoCallbackPoolingConnection(Message message){
        BrowserConnectionManager browserConnectionManager = (BrowserConnectionManager)message.getPayload();
        Boolean canConnect = asyncConnectionManager.canConnect(browserConnectionManager);
        return MessageBuilder.fromMessage(message).withPayload(canConnect).build();
    }
}
