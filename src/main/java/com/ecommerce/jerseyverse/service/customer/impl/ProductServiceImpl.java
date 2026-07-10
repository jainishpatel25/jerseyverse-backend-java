package com.ecommerce.jerseyverse.service.customer.impl;

import com.ecommerce.jerseyverse.dto.response.Product.ProductSummaryResponse;
import com.ecommerce.jerseyverse.mapper.ProductMapper;
import com.ecommerce.jerseyverse.repository.ProductRepository;
import com.ecommerce.jerseyverse.service.customer.ProductService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }


    @Override
    @Transactional(readOnly = true)
    public List<ProductSummaryResponse> getLatestProducts() {

        Pageable pageable = PageRequest.of(0,8);

        return productRepository
                .findAllByOrderByCreatedAtDesc(pageable)
                .stream()
                .map(productMapper::toProductSummaryResponse)
                .toList();
    }
}
