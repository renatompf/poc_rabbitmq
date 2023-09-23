package io.renatofreire.poc_rabbitmqrest.dto;

import java.math.BigDecimal;

public record OperationResponse (
        BigDecimal firstNumber,
        BigDecimal secondNumber,
        String operationSignal,
        BigDecimal result
) {

}
