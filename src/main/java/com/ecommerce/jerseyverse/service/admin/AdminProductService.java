package com.ecommerce.jerseyverse.service.admin;

import com.ecommerce.jerseyverse.dto.request.product.CreateProductRequest;
import com.ecommerce.jerseyverse.dto.request.product.UpdateProductRequest;
import com.ecommerce.jerseyverse.dto.response.Product.AdminProductDetailResponse;
import com.ecommerce.jerseyverse.dto.response.Product.AdminProductResponse;

import java.util.List;

public interface AdminProductService {

    AdminProductResponse createProduct(CreateProductRequest createProductRequest);

    List<AdminProductResponse> getAllProducts();

    AdminProductDetailResponse getProductById(Long productId);

    AdminProductDetailResponse updateProduct(Long productId, UpdateProductRequest request);

    void deleteProduct(Long productId);
}
