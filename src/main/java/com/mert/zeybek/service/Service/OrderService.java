package com.mert.zeybek.service.Service;

import com.mert.zeybek.dto.Response.OrderDetailsResponse;
import com.mert.zeybek.dto.Response.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse placeOrder(Long userId, List<Long> bookIds);

    List<OrderResponse> getOrdersByUserIdOrderedByDate(Long userId);

    OrderDetailsResponse getOrderDetails(Long orderId);
}