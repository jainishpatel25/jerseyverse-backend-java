package com.ecommerce.jerseyverse.service.customer;

import com.ecommerce.jerseyverse.dto.response.Product.ProductSummaryResponse;

import java.util.List;

public interface ProductService {

    List<ProductSummaryResponse> getLatestProducts();

}
