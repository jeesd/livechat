package org.mylivedata.app.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.support.PeriodicTrigger;

/**
 * Created by lubo08 on 30.10.2014.
 */
@Configuration
@IntegrationComponentScan(
		{
			"org.mylivedata.app.connection.integration",
			"org.mylivedata.app.dashboard.integration"
		}
)
@EnableIntegration
public class IntegrationConfiguration {

    @Bean
    @Description("Entry to the log through the gateway.")
    public QueueChannel logSession() {
        return new QueueChannel(100);
    }

    @Bean
    @Description("Log Http Session")
    public MessageChannel chatUser() {
        return new DirectChannel();
    }

    @Bean
    @Description("Log chat Session")
    public MessageChannel removeSession() {
        return new DirectChannel();
    }

    @Bean
    @Description("User go offline notification")
    public MessageChannel userGoOffline() {
        return new DirectChannel();
    }

    @Bean
    @Description("User go offline only if last connection closed")
    public MessageChannel userGoOfflineFilter() {
        return new DirectChannel();
    }

    @Bean
    @Description("Send new user arrived notification to operators")
    public MessageChannel newUserNotificationFilter() {
        return new DirectChannel();
    }

    @Bean
    @Description("Send new user arrived notification to operators")
    public MessageChannel newUserNotification() {
        return new DirectChannel();
    }

    @Bean
    @Description("Check if user changed department location and send info to operators.")
    public MessageChannel changeDepartment() {
        return new DirectChannel();
    }

    @Bean
    @Description("Notify only if department changed")
    public MessageChannel changeDepartmentFilter() {
        return new DirectChannel();
    }

    @Bean
    @Description("Go to rooter and switch to chat user or dashboard user flow.")
    public MessageChannel routeConnection() {
        return new DirectChannel();
    }

    @Bean
    @Description("Replay if user can create direct connection or have to switch to browser local storage")
    public MessageChannel canConnect() {
        return new DirectChannel();
    }

    @Bean
    @Description("Go to process websocket connection")
    public MessageChannel manageWebsocketConnection() {
        return new DirectChannel();
    }

    @Bean
    @Description("Go to process callback pooling connection")
    public MessageChannel manageCallbackPoolingConnection() {
        return new DirectChannel();
    }

    @Bean
    @Description("Disconnect chat and show message to dashboard user that session time out.")
    public MessageChannel disconnectChat() {
        return new DirectChannel();
    }

    @Bean
    @Description("Authorize anonymous user and set session data")
    public MessageChannel authorizeChatUser() {
        return new DirectChannel();
    }


    @Bean
    @Description("Authorize anonymous user and set session data - reply")
    public MessageChannel canHandshake()  {
        return new DirectChannel();
    }

    /*
    @Bean
    @Description("Aggregated Http and Ws log.")
    public MessageChannel aggregatedHttWs() {
        return new DirectChannel();
    }
    */
    @Bean
    public PollerMetadata myPoller() {
        PollerMetadata pollerMetadata = new PollerMetadata();
        pollerMetadata.setTrigger(new PeriodicTrigger(500));
        pollerMetadata.setMaxMessagesPerPoll(10);
        return pollerMetadata;
    }
    
}
