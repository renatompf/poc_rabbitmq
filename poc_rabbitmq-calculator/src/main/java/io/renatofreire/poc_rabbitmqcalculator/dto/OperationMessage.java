package io.renatofreire.poc_rabbitmqcalculator.dto;

import java.math.BigDecimal;

public record OperationMessage(
        BigDecimal firstNumber,
        BigDecimal secondNumber,
        String operationSignal
) {

}
