package com.ecommerce.jerseyverse.controller.customer;

import com.ecommerce.jerseyverse.dto.response.PageResponse;
import com.ecommerce.jerseyverse.dto.response.PriceRangeResponse;
import com.ecommerce.jerseyverse.dto.response.Product.ProductSummaryResponse;
import com.ecommerce.jerseyverse.service.customer.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
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

    @GetMapping
    public ResponseEntity<PageResponse<ProductSummaryResponse>> getProducts(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @PageableDefault(size = 5) Pageable pageable,
            @RequestParam(required = false) String sort
            ) {

        return ResponseEntity.ok(
                productService.getProducts(search, minPrice, maxPrice, sort, pageable)
        );
    }

    @GetMapping("/price-range")
    public ResponseEntity<PriceRangeResponse> getPriceRange() {

        return ResponseEntity.ok(
                productService.getPriceRange()
        );
    }

}
