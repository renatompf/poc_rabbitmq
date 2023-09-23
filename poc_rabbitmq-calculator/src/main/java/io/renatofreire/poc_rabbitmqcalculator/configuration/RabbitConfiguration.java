package io.renatofreire.poc_rabbitmqcalculator.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfiguration {

    public static final String FIRST_QUEUE = "queue";

    public static final String SECOND_QUEUE = "second_queue";

    public static final String EXCHANGE = "exchange";

    @Bean
    Queue messageQueue(){
        return new Queue(FIRST_QUEUE);
    }

    @Bean
    Queue replyQueue(){
        return new Queue(SECOND_QUEUE);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    Binding msgBinding() {
        return BindingBuilder.bind(messageQueue()).to(exchange()).with(FIRST_QUEUE);
    }

    @Bean
    Binding replyBinding() {
        return BindingBuilder.bind(replyQueue()).to(exchange()).with(SECOND_QUEUE);
    }

}
