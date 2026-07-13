package com.ecommerce.jerseyverse.entity;

import com.ecommerce.jerseyverse.common.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(
        name = "cart_items",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_cart_item_cart_variant",
                        columnNames = {
                                "cart_id",
                                "product_variant_id"
                        }
                )
        }
)
public class CartItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "cart_id",
            nullable = false
    )
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "product_variant_id",
            nullable = false
    )
    private ProductVariant productVariant;

    @Column(nullable = false)
    private Integer quantity;

    public CartItem() {
    }

    public CartItem(
            Cart cart,
            ProductVariant productVariant,
            Integer quantity
    ) {
        this.cart = cart;
        this.productVariant = productVariant;
        this.quantity = quantity;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public ProductVariant getProductVariant() {
        return productVariant;
    }

    public void setProductVariant(ProductVariant productVariant) {
        this.productVariant = productVariant;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}