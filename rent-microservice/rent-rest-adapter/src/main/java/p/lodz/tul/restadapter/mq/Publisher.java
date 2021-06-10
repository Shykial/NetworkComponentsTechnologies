package p.lodz.tul.restadapter.mq;

import lombok.extern.java.Log;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

@Component
@Log
public class Publisher {
    private final RabbitTemplate template;
    private final TopicExchange topicExchange;
    public static final String BASE_ROUTING_KEY = "rent.";

    public Publisher(RabbitTemplate template, @Qualifier("accountTopicExchange") TopicExchange topicExchange) {
        this.template = template;
        this.topicExchange = topicExchange;
    }

    public boolean send(String routingKey, Object object) {
        Boolean response = template.convertSendAndReceiveAsType(
                topicExchange.getName(),
                BASE_ROUTING_KEY + routingKey,
                object,
                ParameterizedTypeReference.forType(Boolean.class));

        if (response == null) {
            log.severe("null response");
        }

        return response != null ? response : false;
    }
}
