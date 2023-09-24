package io.renatofreire.poc_rabbitmqcalculator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.renatofreire.poc_rabbitmqcalculator.configuration.RabbitConfiguration;
import io.renatofreire.poc_rabbitmqcalculator.dto.OperationMessage;
import io.renatofreire.poc_rabbitmqcalculator.dto.OperationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class OperationService {

    private static final Logger logger = LoggerFactory.getLogger(OperationService.class);

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper;

    @Autowired
    public OperationService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        objectMapper = new ObjectMapper();
    }

    @RabbitListener(queues = RabbitConfiguration.FIRST_QUEUE)
    public void receiveAndProcess(Message msg) throws IOException {

        OperationMessage operationMessage = objectMapper.readValue(msg.getBody(), OperationMessage.class);
        logger.info("Message received operationMessage={}", operationMessage);

        OperationResponse operationResponse = executeOperation(operationMessage);

        CorrelationData correlationData = new CorrelationData(msg.getMessageProperties().getCorrelationId());

        Message response = MessageBuilder
                .withBody(objectMapper.writeValueAsString(operationResponse).getBytes())
                .setCorrelationId(correlationData.getId())
                .build();

        logger.info("Message to be sent operationMessage={}", response);
        rabbitTemplate.send(RabbitConfiguration.EXCHANGE, RabbitConfiguration.SECOND_QUEUE, response);
    }

    private OperationResponse executeOperation(OperationMessage operationMessage) {
        BigDecimal result = switch (operationMessage.operationSignal()) {
            case "/" -> operationMessage.firstNumber().divide(operationMessage.secondNumber(), 2, RoundingMode.HALF_UP);
            case "*" -> operationMessage.firstNumber().multiply(operationMessage.secondNumber());
            case "-" -> operationMessage.firstNumber().subtract(operationMessage.secondNumber());
            default -> operationMessage.firstNumber().add(operationMessage.secondNumber());
        };

        return new OperationResponse(operationMessage.firstNumber(), operationMessage.secondNumber(), operationMessage.operationSignal(), result);

    }

}
