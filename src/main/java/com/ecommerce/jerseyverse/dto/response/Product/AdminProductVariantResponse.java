package com.ecommerce.jerseyverse.dto.response.Product;

import com.ecommerce.jerseyverse.enums.Size;

public class AdminProductVariantResponse {

    private Size size;

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