package com.mert.zeybek.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class BookExceptionHandler {
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<?> customerNotFoundException(BookNotFoundException bookNotFoundException) {
        List<String> detail = new ArrayList<>();
        detail.add(bookNotFoundException.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("Book Not Found", detail);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
