package com.mert.zeybek.exception;

public class AuthorizationNotFoundException extends RuntimeException{
    public AuthorizationNotFoundException(String message) {
        super(message);
    }
}
