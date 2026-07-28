package com.ecommerce.jerseyverse.dto.response.Product;

import com.ecommerce.jerseyverse.enums.Size;

public class CustomerAvailableSizeResponse {

    private Size size;
    private Long productVariantId;

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Long getProductVariantId() {
        return productVariantId;
    }

    public void setProductVariantId(Long productVariantId) {
        this.productVariantId = productVariantId;
    }
}
