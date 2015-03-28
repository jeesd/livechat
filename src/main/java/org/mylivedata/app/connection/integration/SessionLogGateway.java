package org.mylivedata.app.connection.integration;

import java.util.concurrent.Future;

import org.mylivedata.app.dashboard.domain.custom.SecureUser;
import org.mylivedata.app.webchat.domain.ChatUser;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

/**
 * Created by lubo08 on 29.10.2014.
 */
@MessagingGateway(defaultRequestChannel = "logSession")
public interface SessionLogGateway {

    @Gateway(requestChannel = "logSession", replyChannel = "chatUser")
    ChatUser logSession(@Payload SecureUser userDetails,
                                  @Header("REMOTE_ADDRESS") String remoteAddress,
                                  @Header("CREATION_TIME") long creationTime,
                                  @Header(value = "USER_AGENT", required = false) String userAgent,
                                  @Header(value = "DEPARTMENT", required = false) String departmentKey,
                                  @Header(value = "CHAT_SESSION_ID") String sessionId
    );

    void logWsSession(@Payload SecureUser userDetails,
                                 @Header("CHAT_SESSION_ID") String chatSessionId);

    @Gateway(requestChannel = "changeDepartmentFilter")
    Future<Integer> userReloadInfo(@Payload SecureUser userDetails,
                                   @Header(value = "DEPARTMENT", required = false) String departmentKey,
                                   @Header(value = "URL", required = false) String url
    );

    @Gateway(requestChannel = "removeSession")
    Future<Integer> removeSession(@Payload SecureUser userDetails,
                                  @Header("CHAT_SESSION_ID") String chatSessionId
    );



}
