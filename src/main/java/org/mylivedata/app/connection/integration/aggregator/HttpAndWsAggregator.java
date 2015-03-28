package org.mylivedata.app.connection.integration.aggregator;

import org.springframework.messaging.Message;

/**
 * Created by lubo08 on 29.10.2014.
 */
public class HttpAndWsAggregator {

    //@Aggregator(inputChannel = "logSession", outputChannel = "aggregatedHttWs")
    public Message<?> aggregateIt(Message<?> message) {
        return message;
    }

}
