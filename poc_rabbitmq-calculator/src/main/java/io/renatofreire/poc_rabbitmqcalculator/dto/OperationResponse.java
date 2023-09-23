package io.renatofreire.poc_rabbitmqcalculator.dto;

import java.math.BigDecimal;

public record OperationResponse (
        BigDecimal firstNumber,
        BigDecimal secondNumber,
        String operationSignal,
        BigDecimal result
) {

}
