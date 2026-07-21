package com.ecommerce.jerseyverse.repository;

import com.ecommerce.jerseyverse.entity.Order;
import com.ecommerce.jerseyverse.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {

    Page<Order> findByUser(User user, Pageable pageable);

    Optional<Order> findByIdAndUser(Long id, User user);

    Optional<Order> findTopByOrderByIdDesc();

    @Query("""
    SELECT COALESCE(SUM(o.totalAmount), 0)
    FROM Order o
    WHERE o.status = com.ecommerce.jerseyverse.enums.OrderStatus.DELIVERED
""")
    BigDecimal getTotalRevenueByDeliveredOrders();
}
