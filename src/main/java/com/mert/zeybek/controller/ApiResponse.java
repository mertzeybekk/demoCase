package com.mert.zeybek.controller;

import lombok.Data;
import org.springframework.hateoas.Link;

@Data
public class ApiResponse<T> {
    private String message;
    private T data;
    private Link link;

}
