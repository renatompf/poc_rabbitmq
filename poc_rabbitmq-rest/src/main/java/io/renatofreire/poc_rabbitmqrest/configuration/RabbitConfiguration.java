package io.renatofreire.poc_rabbitmqrest.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
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
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setReplyAddress(SECOND_QUEUE);
        template.setReplyTimeout(20000);
        return template;
    }

    @Bean
    SimpleMessageListenerContainer replyContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(SECOND_QUEUE);
        container.setMessageListener(rabbitTemplate(connectionFactory));
        return container;
    }

}
