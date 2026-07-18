package com.ecommerce.jerseyverse.repository;

import com.ecommerce.jerseyverse.entity.OrderAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderAddressRepository extends JpaRepository<OrderAddress, Long> {
}
