package com.ecommerce.jerseyverse.dto.response.Product;

import com.ecommerce.jerseyverse.enums.Size;

public class CustomerAvailableSizeResponse {

    private Size size;

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }
}
