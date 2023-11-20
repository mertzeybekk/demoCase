package com.mert.zeybek.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class OrderExceptionHandler {
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<?> customerNotFoundException(OrderNotFoundException orderNotFoundException) {
        List<String> detail = new ArrayList<>();
        detail.add(orderNotFoundException.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("Order Not Found", detail);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
