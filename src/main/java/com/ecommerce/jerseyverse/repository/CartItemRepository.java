package com.ecommerce.jerseyverse.repository;

import com.ecommerce.jerseyverse.entity.Cart;
import com.ecommerce.jerseyverse.entity.CartItem;
import com.ecommerce.jerseyverse.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository
        extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCartAndProductVariant(
            Cart cart,
            ProductVariant productVariant
    );

    Optional<CartItem> findByIdAndCart(
            Long id,
            Cart cart
    );

    boolean existsByProductVariantProductId(Long productId);
}
