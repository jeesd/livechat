package org.mylivedata.app.connection.integration.flow;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.core.Pollers;
import org.springframework.scheduling.support.PeriodicTrigger;

@Configuration
public class SessionManegementFlows {
	
	@Bean
    @Description("Register session when user go online")
    public IntegrationFlow sessionLogFlow() {
        return IntegrationFlows.from("logSession")
                // Convert user agent header from string to object
                .handle("sessionLogService", "convertUserAgentHeader",
                        c -> c.poller(Pollers.trigger(new PeriodicTrigger(500)).maxMessagesPerPoll(10)))
                // register user session into db
                .handle("sessionLogService", "logUserSession")
                // insert user info into ehcache
                .handle("sessionLogService", "registerChatUserIntoCache")
                .split()
                // replay to finish flow and split and continue newUserNotificationFlow so other operators see new user arrived.
                .route("sessionLogTypeRouter", "routeChatOrNotification")
                // return user
                .channel("chatUser")
                .get();
    }

    

    @Bean
    @Description("Remove session when destroyed")
    public IntegrationFlow sessionRemovedFlow() {
        return IntegrationFlows.from("removeSession")
                // remove chat session from user sessions map
                .handle("sessionLogService", "removeChatSession")
                // continue and remove user from ehcache only if user have no more opened sessions.
                .filter("@notificationFilter.isNoSession(payload)")
                // remove user from ehcache
                .handle("sessionLogService", "removeChatUser")
                // update session log with end timestamp
                .handle("sessionLogService", "endUserSession")
                // notify that user is now offline.
                .handle("sessionLogService", "userGoOfflineNotification")
                .get();
    }

    @Bean
    @Description("Remove session when destroyed")
    public IntegrationFlow authorizeAnonymousChatUser() {
        return IntegrationFlows.from("authorizeChatUser")
                .handle("authorizationService", "validateUserHashId")
                // validate if visitor can connect and return user detail.
                .handle("authorizationService", "validateDomainVsAccount")
                .get();
    }
	
}
