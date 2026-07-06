package com.ecommerce.jerseyverse.dto.request.product;

import com.ecommerce.jerseyverse.enums.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ProductVariantRequest {

    @NotNull(message = "Product size is required.")
    private Size size;

    @NotNull(message = "Stock quantity is required.")
    @Min(value = 0, message = "Stock cannot be negative.")
    private Integer stock;

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}