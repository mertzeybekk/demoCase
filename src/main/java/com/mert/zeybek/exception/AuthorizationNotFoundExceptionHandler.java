package com.mert.zeybek.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class AuthorizationNotFoundExceptionHandler {
    @ExceptionHandler(AuthorizationNotFoundException.class)
    public ResponseEntity<?> customerNotFoundException(AuthorizationNotFoundException authorizationNotFoundException) {
        List<String> detail = new ArrayList<>();
        detail.add(authorizationNotFoundException.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("Authorization Not Found", detail);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
