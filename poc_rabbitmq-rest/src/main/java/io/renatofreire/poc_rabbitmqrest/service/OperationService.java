package io.renatofreire.poc_rabbitmqrest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.renatofreire.poc_rabbitmqrest.configuration.RabbitConfiguration;
import io.renatofreire.poc_rabbitmqrest.dto.OperationMessage;
import io.renatofreire.poc_rabbitmqrest.dto.OperationRequest;
import io.renatofreire.poc_rabbitmqrest.dto.OperationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

@Service
public class OperationService {

    private static final Logger logger = LoggerFactory.getLogger(OperationService.class);

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper;


    @Autowired
    public OperationService(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public OperationResponse sum(OperationRequest operation)  {

        OperationMessage operationMessage = new OperationMessage(operation.firstNumber(), operation.secondNumber(), "+");
        
        return sendMessage(operationMessage);
    }

    public OperationResponse substraction(OperationRequest operation)  {

        OperationMessage operationMessage = new OperationMessage(operation.firstNumber(), operation.secondNumber(), "-");

        return sendMessage(operationMessage);
    }

    public OperationResponse division(OperationRequest operation)  {
        if(BigDecimal.ZERO.equals(operation.secondNumber())){
            return new OperationResponse(null, null, "Division by 0 not possible" ,null);
        }

        OperationMessage operationMessage = new OperationMessage(operation.firstNumber(), operation.secondNumber(), "/");

        return sendMessage(operationMessage);
    }

    public OperationResponse multiplication(OperationRequest operation)  {

        OperationMessage operationMessage = new OperationMessage(operation.firstNumber(), operation.secondNumber(), "*");

        return sendMessage(operationMessage);
        
    }
    
    private OperationResponse sendMessage(OperationMessage operationMessage)  {
        try{
            Message messageToSend = MessageBuilder
                    .withBody(objectMapper.writeValueAsString(operationMessage).getBytes())
                    .setCorrelationId(UUID.randomUUID().toString())
                    .build();
    
            logger.info("sent message = {}", messageToSend);
            Message result = rabbitTemplate.sendAndReceive(RabbitConfiguration.EXCHANGE, RabbitConfiguration.FIRST_QUEUE, messageToSend);
    
            logger.info("received the following: = {}", result);
            if (result != null) {
                return objectMapper.readValue(result.getBody(), OperationResponse.class);
            }
        }catch (IOException exception){
            logger.error("Something went wrong while communication with the broker or serializing the messages. Exception: " + exception.getMessage());
        }

        return null;
    }
    
}
