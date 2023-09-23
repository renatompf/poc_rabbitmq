package io.renatofreire.poc_rabbitmqrest.dto;

import java.math.BigDecimal;

public record OperationRequest(
        BigDecimal firstNumber,
        BigDecimal secondNumber
) {

}
