package com.ecommerce.jerseyverse.dto.response.Product;

import java.math.BigDecimal;
import java.util.List;

public class ProductDetailResponse {

    private Long id;

    private String name;

    private String description;

    private String imageUrl;

    private BigDecimal price;

    public List<CustomerAvailableSizeResponse> getAvailableSizes() {
        return availableSizes;
    }

    public void setAvailableSizes(List<CustomerAvailableSizeResponse> availableSizes) {
        this.availableSizes = availableSizes;
    }

    private List<CustomerAvailableSizeResponse> availableSizes;

    private String stockStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    public String getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(String stockStatus) {
        this.stockStatus = stockStatus;
    }
}