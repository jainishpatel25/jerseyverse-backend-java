package com.ecommerce.jerseyverse.repository;

import com.ecommerce.jerseyverse.entity.Product;
import com.ecommerce.jerseyverse.entity.ProductVariant;
import com.ecommerce.jerseyverse.enums.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {

    List<ProductVariant> findByProduct(Product product);

    Optional<ProductVariant> findByProductAndSize(Product product, Size size);

    boolean existsByProductAndSize(Product product, Size size);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM ProductVariant pv WHERE pv.product = :product")
    void deleteByProduct(@Param("product") Product product);

}