package p.lodz.tul.restadapter.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;

@Configuration
public class ConsumerConfiguration {
    public static final String RENT_BASE_ROUTING_KEY = "rent.*";
    public static final String EXCHANGE = "account_exchange";
    public static final String RENT_QUEUE_NAME = "account_rent_queue";

    @Bean
    public TopicExchange accountTopicExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue rentQueue() {
        return new Queue(RENT_QUEUE_NAME);
    }

    @Bean
    public Binding rentBinding(@Qualifier("accountTopicExchange") TopicExchange topicExchange, Queue queue) {
        return BindingBuilder.bind(queue)
                .to(topicExchange)
                .with(RENT_BASE_ROUTING_KEY);
    }
}
