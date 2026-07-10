package com.ecommerce.jerseyverse.controller.customer;

import com.ecommerce.jerseyverse.dto.response.Product.ProductSummaryResponse;
import com.ecommerce.jerseyverse.service.customer.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/latest")
    public ResponseEntity<List<ProductSummaryResponse>> getLatestProducts() {

        return ResponseEntity.ok(
                productService.getLatestProducts()
        );
    }

}
