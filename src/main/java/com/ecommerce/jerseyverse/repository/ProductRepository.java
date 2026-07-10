package com.ecommerce.jerseyverse.repository;

import com.ecommerce.jerseyverse.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByName(String name);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    List<Product> findAllByOrderByCreatedAtDesc(Pageable pageable);

}