package com.mert.zeybek.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class OutOfStockExceptionHandler {
    @ExceptionHandler(OutOfStockException.class)
    public ResponseEntity<?> customerNotFoundException(OutOfStockException ofStockException) {
        List<String> detail = new ArrayList<>();
        detail.add(ofStockException.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("The book is not available in stock", detail);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
