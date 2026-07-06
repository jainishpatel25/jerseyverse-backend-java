package com.ecommerce.jerseyverse.dto.request.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class UpdateProductRequest {

    @NotBlank(message = "Product name is required.")
    private String name;

    @NotBlank(message = "Product description is required.")
    private String description;

    @NotNull(message = "Product price is required.")
    @Positive(message = "Product price must be greater than zero.")
    private BigDecimal price;

    @NotNull(message = "Category is required.")
    private Long categoryId;

    @NotBlank(message = "Product image URL is required.")
    private String imageUrl;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}