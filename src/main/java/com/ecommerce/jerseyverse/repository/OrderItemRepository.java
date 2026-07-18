package com.ecommerce.jerseyverse.repository;

import com.ecommerce.jerseyverse.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
