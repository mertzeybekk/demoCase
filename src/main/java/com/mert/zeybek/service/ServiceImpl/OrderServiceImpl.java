package com.mert.zeybek.service.ServiceImpl;

import com.mert.zeybek.dto.Response.OrderDetailsResponse;
import com.mert.zeybek.dto.Response.OrderResponse;
import com.mert.zeybek.exception.*;
import com.mert.zeybek.model.Book;
import com.mert.zeybek.model.Order;
import com.mert.zeybek.model.User;
import com.mert.zeybek.repository.BookRepository;
import com.mert.zeybek.repository.OrderRepository;
import com.mert.zeybek.repository.UserRepository;
import com.mert.zeybek.service.Service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper, BookRepository bookRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Override
    public OrderResponse placeOrder(Long userId, List<Long> bookIds) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        List<Book> books = bookRepository.findAllById(bookIds);

        checkIfBooksAreInStock(books);

        double totalPrice = calculateTotalPrice(books);
        validateMinimumOrderPrice(totalPrice);

        updateStockQuantities(books);

        Order order = createOrder(user, books, totalPrice);

        Order savedOrder = orderRepository.save(order);

        return modelMapper.map(savedOrder, OrderResponse.class);
    }

    private void checkIfBooksAreInStock(List<Book> books) {
        for (Book book : books) {
            if (book.getStockQuantity() <= 0) {
                throw new OutOfStockException("Book is out of stock: " + book.getIsbn());
            }
        }
    }

    private double calculateTotalPrice(List<Book> books) {
        return books.stream().mapToDouble(Book::getPrice).sum();
    }

    private void validateMinimumOrderPrice(double totalPrice) {
        if (totalPrice < 25.0) {
            throw new MinimumOrderPriceException("Minimum order price is $25");
        }
    }

    private void updateStockQuantities(List<Book> books) {
        for (Book book : books) {
            int remainingStock = book.getStockQuantity() - 1;
            book.setStockQuantity(remainingStock);
            bookRepository.save(book);
        }
    }

    private Order createOrder(User user, List<Book> books, double totalPrice) {
        Order order = new Order();
        order.setUser(user);
        order.setBooks(books);
        order.setTotalPrice(totalPrice);
        order.setOrderDate(new Date());
        order.setCreatedAt(new Date());
        order.setUpdatedAt(new Date());
        return order;
    }

    @Override
    public List<OrderResponse> getOrdersByUserIdOrderedByDate(Long userId) {
        List<Order> userOrders = orderRepository.findAllByUserIdOrderByUpdatedAtDesc(userId);
        return userOrders.stream()
                .map(order -> modelMapper.map(order, OrderResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrderDetailsResponse getOrderDetails(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));

        return modelMapper.map(order, OrderDetailsResponse.class);
    }
}
