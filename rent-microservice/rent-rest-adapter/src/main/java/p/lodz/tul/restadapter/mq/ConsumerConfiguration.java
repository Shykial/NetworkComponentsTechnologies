package p.lodz.tul.restadapter.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsumerConfiguration {
    public static final String VEHICLE_BASE_ROUTING_KEY = "vehicle.*";
    public static final String ACCOUNT_BASE_ROUTING_KEY = "account.*";
    public static final String EXCHANGE = "rent_exchange";
    public static final String VEHICLE_QUEUE_NAME = "rent_vehicle_queue";
    public static final String ACCOUNT_QUEUE_NAME = "rent_account_queue";

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue vehicleQueue() {
        return new Queue(VEHICLE_QUEUE_NAME);
    }

    @Bean
    public Queue accountQueue() {
        return new Queue(ACCOUNT_QUEUE_NAME);
    }

    @Bean
    public Binding vehicleBinding(TopicExchange topicExchange,
                                  @Qualifier("vehicleQueue") Queue queue) {
        return BindingBuilder.bind(queue)
                .to(topicExchange)
                .with(VEHICLE_BASE_ROUTING_KEY);
    }

    @Bean
    public Binding accountBinding(TopicExchange topicExchange,
                                  @Qualifier("accountQueue") Queue queue) {
        return BindingBuilder.bind(queue)
                .to(topicExchange)
                .with(ACCOUNT_BASE_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
