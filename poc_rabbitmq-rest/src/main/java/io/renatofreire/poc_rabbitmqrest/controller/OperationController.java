package io.renatofreire.poc_rabbitmqrest.controller;

import io.renatofreire.poc_rabbitmqrest.dto.OperationRequest;
import io.renatofreire.poc_rabbitmqrest.dto.OperationResponse;
import io.renatofreire.poc_rabbitmqrest.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/operations")
public class OperationController {

    private final OperationService operationService;

    @Autowired
    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @PostMapping("/sum")
    public ResponseEntity<OperationResponse> sum(@RequestBody OperationRequest operationRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(operationService.sum(operationRequest));
    }

    @PostMapping("/substraction")
    public ResponseEntity<OperationResponse> substraction(@RequestBody OperationRequest operationRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(operationService.substraction(operationRequest));
    }

    @PostMapping("/division")
    public ResponseEntity<OperationResponse> division(@RequestBody OperationRequest operationRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(operationService.division(operationRequest));
    }

    @PostMapping("/multiplication")
    public ResponseEntity<OperationResponse> multiplication(@RequestBody OperationRequest operationRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(operationService.multiplication(operationRequest));
    }

}
