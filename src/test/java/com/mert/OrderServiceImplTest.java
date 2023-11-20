package com.mert;

import com.mert.zeybek.dto.Response.OrderDetailsResponse;
import com.mert.zeybek.dto.Response.OrderResponse;
import com.mert.zeybek.exception.MinimumOrderPriceException;
import com.mert.zeybek.exception.OrderNotFoundException;
import com.mert.zeybek.model.Book;
import com.mert.zeybek.model.Order;
import com.mert.zeybek.model.User;
import com.mert.zeybek.repository.BookRepository;
import com.mert.zeybek.repository.OrderRepository;
import com.mert.zeybek.repository.UserRepository;
import com.mert.zeybek.service.ServiceImpl.OrderServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    public void testPlaceOrder_Success() {
        // Mocking the UserRepository
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));

        // Mocking the BookRepository
        when(bookRepository.findAllById(anyList())).thenReturn(Arrays.asList(new Book(), new Book()));

        // Mocking the OrderRepository
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order savedOrder = invocation.getArgument(0);
            savedOrder.setOrderId(1L); // Set an example ID for testing
            return savedOrder;
        });

        // Mocking the ModelMapper
        when(modelMapper.map(any(Order.class), eq(OrderResponse.class))).thenReturn(new OrderResponse());

        OrderResponse orderResponse = orderService.placeOrder(1L, Arrays.asList(1L, 2L));

        assertNotNull(orderResponse);
        // Add more assertions based on your business logic
    }

    @Test
    public void testPlaceOrder_MinimumOrderPriceException() {
        // Mocking the UserRepository
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));

        // Mocking the BookRepository
        when(bookRepository.findAllById(anyList())).thenReturn(Collections.singletonList(new Book()));

        // Ensure the total price is below the minimum order price
        when(modelMapper.map(any(Order.class), eq(OrderResponse.class))).thenReturn(new OrderResponse());

        assertThrows(MinimumOrderPriceException.class, () -> {
            orderService.placeOrder(1L, Collections.singletonList(1L));
        });
    }

    @Test
    public void testGetOrdersByUserIdOrderedByDate() {
        // Mocking the OrderRepository
        when(orderRepository.findAllByUserIdOrderByUpdatedAtDesc(anyLong())).thenReturn(Arrays.asList(new Order(), new Order()));

        // Mocking the ModelMapper
        when(modelMapper.map(any(Order.class), eq(OrderResponse.class))).thenReturn(new OrderResponse());

        List<OrderResponse> orderResponses = orderService.getOrdersByUserIdOrderedByDate(1L);

        assertNotNull(orderResponses);
        // Add more assertions based on your business logic
    }

    @Test
    public void testGetOrderDetails_Success() {
        // Mocking the OrderRepository
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(new Order()));

        // Mocking the ModelMapper
        when(modelMapper.map(any(Order.class), eq(OrderDetailsResponse.class))).thenReturn(new OrderDetailsResponse());

        OrderDetailsResponse orderDetailsResponse = orderService.getOrderDetails(1L);

        assertNotNull(orderDetailsResponse);
        // Add more assertions based on your business logic
    }

    @Test
    public void testGetOrderDetails_OrderNotFoundException() {
        // Mocking the OrderRepository to return an empty Optional
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> {
            orderService.getOrderDetails(1L);
        });
    }
}