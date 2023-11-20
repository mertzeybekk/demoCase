package com.mert.zeybek.repository;

import com.mert.zeybek.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findAllByUserIdOrderByUpdatedAtDesc(Long userId);
    Optional<Order> findByOrderId(Long orderId);

}
