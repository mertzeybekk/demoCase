package com.mert.zeybek.controller;

import com.mert.zeybek.dto.Request.OrderRequest;
import com.mert.zeybek.dto.Response.OrderDetailsResponse;
import com.mert.zeybek.dto.Response.OrderResponse;
import com.mert.zeybek.service.Service.OrderService;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> placeOrder(@RequestBody OrderRequest orderRequest) {
        OrderResponse orderResponse = orderService.placeOrder(orderRequest.getUserId(), orderRequest.getBookIds());
        ApiResponse<OrderResponse> response = new ApiResponse<>();
        response.setMessage("Order placed successfully");
        response.setData(orderResponse);
        Link selfLink = linkTo(methodOn(OrderController.class).placeOrder(orderRequest)).withSelfRel().withType("POST");
        orderResponse.add(selfLink);
        response.setLink(selfLink);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getOrdersByUserIdOrderedByDate(@PathVariable Long userId) {
        List<OrderResponse> orderResponses = orderService.getOrdersByUserIdOrderedByDate(userId);
        ApiResponse<List<OrderResponse>> response = new ApiResponse<>();
        response.setMessage("Orders retrieved successfully");
        response.setData(orderResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/details/{orderId}")
    public ResponseEntity<ApiResponse<OrderDetailsResponse>> getOrderDetails(@PathVariable Long orderId) {
        OrderDetailsResponse orderDetailsResponse = orderService.getOrderDetails(orderId);
        ApiResponse<OrderDetailsResponse> response = new ApiResponse<>();
        response.setMessage("Order details retrieved successfully");
        response.setData(orderDetailsResponse);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}