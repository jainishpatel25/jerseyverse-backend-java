package com.ecommerce.jerseyverse.service.admin;

import com.ecommerce.jerseyverse.dto.request.product.CreateProductRequest;
import com.ecommerce.jerseyverse.dto.request.product.UpdateProductRequest;
import com.ecommerce.jerseyverse.dto.response.Product.AdminProductDetailResponse;
import com.ecommerce.jerseyverse.dto.response.Product.AdminProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AdminProductService {

    AdminProductResponse createProduct(CreateProductRequest createProductRequest, MultipartFile image);

    List<AdminProductResponse> getAllProducts();

    AdminProductDetailResponse getProductById(Long productId);

    AdminProductDetailResponse updateProduct(Long productId, UpdateProductRequest request,MultipartFile image);

    void deleteProduct(Long productId);
}
