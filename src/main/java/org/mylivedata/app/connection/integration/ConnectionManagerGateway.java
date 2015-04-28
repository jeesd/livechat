package org.mylivedata.app.connection.integration;

import java.util.concurrent.Future;

import org.mylivedata.app.connection.domain.VisitorPrincipal;
import org.mylivedata.app.connection.BrowserConnectionManager;
import org.mylivedata.app.dashboard.domain.custom.SecureUser;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

/**
 * Created by lubo08 on 13.11.2014.
 */
@MessagingGateway(defaultRequestChannel = "routeConnection")
public interface ConnectionManagerGateway {

    @Gateway(requestChannel = "routeConnection", replyChannel = "canConnect")
    public boolean allowDirectConnectionOrSwitchToClient(@Payload BrowserConnectionManager browserConnectionManager);

    @Gateway(requestChannel = "userGoOfflineFilter")
    Future<Integer> notifyUserGoOffline(@Payload SecureUser user);

    @Gateway(requestChannel = "authorizeChatUser", replyChannel = "canHandshake")
    public boolean authorizeChatUser(@Payload VisitorPrincipal visitorPrincipal,
                                        @Header("BID") String bid,
                                        @Header("CSID") String currentCometSessionId);


}
