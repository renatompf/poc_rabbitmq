package io.renatofreire.poc_rabbitmqrest.dto;

import java.math.BigDecimal;

public record OperationMessage(
        BigDecimal firstNumber,
        BigDecimal secondNumber,
        String operationSignal
) {

    public OperationMessage(BigDecimal firstNumber, BigDecimal secondNumber, String operationSignal) {
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
        this.operationSignal = operationSignal;
    }
}
