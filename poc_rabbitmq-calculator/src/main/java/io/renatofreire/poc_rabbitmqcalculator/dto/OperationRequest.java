package io.renatofreire.poc_rabbitmqcalculator.dto;

import java.math.BigDecimal;

public record OperationRequest(
        BigDecimal firstNumber,
        BigDecimal secondNumber
) {

}
