package org.mylivedata.app.dashboard.integration.flow;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;

@Configuration
public class NotificationFlows {
	
	@Bean
    @Description("Notify about new user go online")
    public IntegrationFlow newUserNotificationFlow() {
        return IntegrationFlows.from("newUserNotification")
                .handle("sessionLogService", "notifyAboutNewUserOnline")
                .get();
    }
	
}
