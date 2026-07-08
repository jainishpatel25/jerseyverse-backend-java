package com.ecommerce.jerseyverse.controller.admin;

import com.ecommerce.jerseyverse.dto.request.product.CreateProductRequest;
import com.ecommerce.jerseyverse.dto.request.product.UpdateProductRequest;
import com.ecommerce.jerseyverse.dto.response.Product.AdminProductDetailResponse;
import com.ecommerce.jerseyverse.dto.response.Product.AdminProductResponse;
import com.ecommerce.jerseyverse.service.admin.AdminProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/products")
public class AdminProductController {

    private final AdminProductService adminProductService;

    public AdminProductController(AdminProductService adminProductService) {
        this.adminProductService = adminProductService;
    }

    @PostMapping
    public ResponseEntity<AdminProductResponse> createProduct(
            @Valid @RequestBody CreateProductRequest request) {

        AdminProductResponse response =
                adminProductService.createProduct(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<AdminProductResponse>> getAllProducts() {

        List<AdminProductResponse> products =
                adminProductService.getAllProducts();

        return ResponseEntity.ok(products);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<AdminProductDetailResponse> getProductById(
            @PathVariable Long productId) {

        AdminProductDetailResponse response =
                adminProductService.getProductById(productId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<AdminProductDetailResponse> updateProduct(
            @PathVariable Long productId,
            @Valid @RequestBody UpdateProductRequest request
    ) {

        AdminProductDetailResponse response =
                adminProductService.updateProduct(
                        productId,
                        request
                );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long productId) {

        adminProductService.deleteProduct(productId);

        return ResponseEntity.noContent().build();
    }

}