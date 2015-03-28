package org.mylivedata.app.dashboard.integration;

import java.util.concurrent.Future;

import org.mylivedata.app.dashboard.domain.custom.SecureUser;
import org.mylivedata.app.webchat.domain.ChatUser;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Payload;


/**
 * Created by lubo08 on 5.11.2014.
 */
@MessagingGateway(defaultRequestChannel = "logSession")
public interface DashboardGateway {

    Future<Integer> wsSendNewUserToDashboard(@Payload ChatUser chatUser);

    Future<Integer> wsSendUserStateChangedToDashboard(@Payload ChatUser chatUser);

    Future<Integer> wsSendUserRemovedToDashboard(@Payload ChatUser chatUser);

    @Gateway(requestChannel = "disconnectChat")
    void disconnectChatAfterHttpSessionTimeOut(@Payload SecureUser chatUser);

}
