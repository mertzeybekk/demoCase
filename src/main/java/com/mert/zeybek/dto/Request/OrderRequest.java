package com.mert.zeybek.dto.Request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

public class OrderRequest {

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @Min(value = 25, message = "Total price must be at least 25$")
    private double totalPrice;

    @NotNull(message = "Books list cannot be null")
    private List<Long> bookIds;

    public OrderRequest(Long userId, double totalPrice, List<Long> bookIds) {
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.bookIds = bookIds;
    }
    public OrderRequest(){}

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<Long> getBookIds() {
        return bookIds;
    }

    public void setBookIds(List<Long> bookIds) {
        this.bookIds = bookIds;
    }
}