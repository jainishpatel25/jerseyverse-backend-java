package com.ecommerce.jerseyverse.mapper;


import com.ecommerce.jerseyverse.dto.request.product.CreateProductRequest;
import com.ecommerce.jerseyverse.dto.request.product.UpdateProductRequest;
import com.ecommerce.jerseyverse.dto.response.Product.*;
import com.ecommerce.jerseyverse.entity.Product;
import com.ecommerce.jerseyverse.entity.ProductVariant;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {

    /**
     * Request DTO -> Entity
     */
    public Product toEntity(CreateProductRequest request) {

        Product product = new Product();

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setImageUrl(request.getImageUrl());

        return product;
    }

    /**
     * Update existing entity from request DTO
     */
    public void updateEntity(Product product, UpdateProductRequest request) {

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setImageUrl(request.getImageUrl());
    }

    /**
     * Entity -> Customer Summary Response
     */
    public ProductSummaryResponse toProductSummaryResponse(Product product) {

        ProductSummaryResponse response = new ProductSummaryResponse();

        response.setId(product.getId());
        response.setName(product.getName());
        response.setImageUrl(product.getImageUrl());
        response.setPrice(product.getPrice());
        response.setStockStatus(calculateStockStatus(product));

        return response;
    }

    /**
     * Entity -> Customer Detail Response
     */
    public ProductDetailResponse toProductDetailResponse(Product product) {

        ProductDetailResponse response = new ProductDetailResponse();

        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setImageUrl(product.getImageUrl());
        response.setPrice(product.getPrice());
        response.setStockStatus(calculateStockStatus(product));

        List<CustomerAvailableSizeResponse> availableSizes = product.getVariants()
                .stream()
                .filter(variant -> variant.getStock() > 0)
                .map(this::toCustomerAvailableSizeResponse)
                .toList();

        response.setAvailableSizes(availableSizes);

        return response;
    }

    /**
     * Entity -> Admin Response
     */
    public AdminProductResponse toAdminProductResponse(Product product) {

        AdminProductResponse response = new AdminProductResponse();

        response.setId(product.getId());
        response.setName(product.getName());
        response.setImageUrl(product.getImageUrl());
        response.setPrice(product.getPrice());
        response.setTotalStock(calculateTotalStock(product));

        return response;
    }

    /**
     * ProductVariant -> Customer Available Size Response
     */
    private CustomerAvailableSizeResponse toCustomerAvailableSizeResponse(ProductVariant variant) {

        CustomerAvailableSizeResponse response = new CustomerAvailableSizeResponse();

        response.setSize(variant.getSize());

        return response;
    }

    /**
     * Calculate total stock of a product.
     */
    private Integer calculateTotalStock(Product product) {

        return product.getVariants()
                .stream()
                .mapToInt(ProductVariant::getStock)
                .sum();
    }

    /**
     * Calculate stock status for customer APIs.
     */
    private String calculateStockStatus(Product product) {

        return calculateTotalStock(product) > 0
                ? "IN_STOCK"
                : "OUT_OF_STOCK";
    }

    public AdminProductDetailResponse toAdminProductDetailResponse(Product product) {

        AdminProductDetailResponse response = new AdminProductDetailResponse();

        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setImageUrl(product.getImageUrl());
        response.setCategoryId(product.getCategory().getId());

        return response;
    }

}