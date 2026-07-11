package com.ecommerce.jerseyverse.service.customer;

import com.ecommerce.jerseyverse.dto.response.PageResponse;
import com.ecommerce.jerseyverse.dto.response.PriceRangeResponse;
import com.ecommerce.jerseyverse.dto.response.Product.ProductDetailResponse;
import com.ecommerce.jerseyverse.dto.response.Product.ProductSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    List<ProductSummaryResponse> getLatestProducts();

    PageResponse<ProductSummaryResponse> getProducts(String search, BigDecimal minPrice, BigDecimal maxPrice, String sort, Pageable pageable);

    PriceRangeResponse getPriceRange();

    ProductDetailResponse getProductById(Long productId);
}
