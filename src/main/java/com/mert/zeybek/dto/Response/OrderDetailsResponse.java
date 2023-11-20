package com.mert.zeybek.dto.Response;

import com.mert.zeybek.model.Order;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

// OrderDetailsResponse.java

public class OrderDetailsResponse {
    private Long orderId;
    private double totalPrice;
    private Date orderDate;
    private List<BookResponse> books;

    public OrderDetailsResponse(Order order) {
        this.orderId = order.getOrderId();
        this.totalPrice = order.getTotalPrice();
        this.orderDate = order.getOrderDate();
        this.books = order.getBooks().stream()
                .map(BookResponse::new)
                .collect(Collectors.toList());
    }
    public OrderDetailsResponse(){}

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public List<BookResponse> getBooks() {
        return books;
    }

    public void setBooks(List<BookResponse> books) {
        this.books = books;
    }
}
