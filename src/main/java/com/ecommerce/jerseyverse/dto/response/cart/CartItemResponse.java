package com.ecommerce.jerseyverse.dto.response.cart;

import com.ecommerce.jerseyverse.enums.CartItemAvailabilityStatus;

import java.math.BigDecimal;

public class CartItemResponse {

    private Long cartItemId;

    private Long productId;

    private String productName;

    private String imageUrl;

    private String size;

    private Integer quantity;

    private BigDecimal unitPrice;

    private BigDecimal itemSubtotal;

    private CartItemAvailabilityStatus availabilityStatus;

    public CartItemResponse() {
    }

    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getItemSubtotal() {
        return itemSubtotal;
    }

    public void setItemSubtotal(BigDecimal itemSubtotal) {
        this.itemSubtotal = itemSubtotal;
    }

    public CartItemAvailabilityStatus getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(
            CartItemAvailabilityStatus availabilityStatus
    ) {
        this.availabilityStatus = availabilityStatus;
    }
}
