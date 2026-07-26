package com.ecommerce.jerseyverse.controller.admin;

import com.ecommerce.jerseyverse.dto.request.product.CreateProductRequest;
import com.ecommerce.jerseyverse.dto.request.product.UpdateProductRequest;
import com.ecommerce.jerseyverse.dto.response.Product.AdminProductDetailResponse;
import com.ecommerce.jerseyverse.dto.response.Product.AdminProductResponse;
import com.ecommerce.jerseyverse.service.admin.AdminProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/products")
public class AdminProductController {

    private final AdminProductService adminProductService;

    public AdminProductController(AdminProductService adminProductService) {
        this.adminProductService = adminProductService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdminProductResponse> createProduct(

            @Valid @RequestPart("product") CreateProductRequest request, @RequestPart("image")MultipartFile image) {

        AdminProductResponse response =
                adminProductService.createProduct(request, image);

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

    @PutMapping(
            value = "/{productId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<AdminProductDetailResponse> updateProduct(
            @PathVariable Long productId,

            @Valid
            @RequestPart("product")
            UpdateProductRequest request,

            @RequestPart(
                    value = "image",
                    required = false
            )
            MultipartFile image
    ) {

        AdminProductDetailResponse response =
                adminProductService.updateProduct(
                        productId,
                        request,
                        image
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