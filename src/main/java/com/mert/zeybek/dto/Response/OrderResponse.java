package com.mert.zeybek.dto.Response;

import com.mert.zeybek.model.Order;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

public class OrderResponse  extends RepresentationModel<OrderResponse> {
    private Long orderId;
    private double totalPrice;
    private Date orderDate;

    public OrderResponse(Order order) {
        this.orderId = order.getOrderId();
        this.totalPrice = order.getTotalPrice();
        this.orderDate = order.getOrderDate();
    }
    public  OrderResponse(){}

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
}
