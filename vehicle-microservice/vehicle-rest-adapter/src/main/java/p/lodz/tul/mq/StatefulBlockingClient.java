package p.lodz.tul.mq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StatefulBlockingClient {

    private final RabbitTemplate template;
    private final DirectExchange directExchange;
    public static final String BASE_ROUTING_KEY = "vehicle_microservice.";

    public Boolean send(String routingKey, Object object) {
        return template.convertSendAndReceiveAsType(
                directExchange.getName(),
                BASE_ROUTING_KEY + routingKey,
                object,
                ParameterizedTypeReference.forType(Boolean.class));
    }
}
