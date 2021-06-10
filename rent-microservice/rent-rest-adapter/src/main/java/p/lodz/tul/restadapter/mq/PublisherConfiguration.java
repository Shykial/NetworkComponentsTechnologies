package p.lodz.tul.restadapter.mq;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PublisherConfiguration {
    public static final String EXCHANGE = "account_exchange";

    @Bean
    public TopicExchange accountTopicExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public MessageConverter jackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}

