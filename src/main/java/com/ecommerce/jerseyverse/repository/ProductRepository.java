package com.ecommerce.jerseyverse.repository;

import com.ecommerce.jerseyverse.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    Optional<Product> findByName(String name);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    List<Product> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<Product> findAll(Pageable pageable);

    @Query("SELECT MIN(p.price) FROM Product p")
    BigDecimal findMinimumPrice();

    @Query("SELECT MAX(p.price) FROM Product p")
    BigDecimal findMaximumPrice();
}