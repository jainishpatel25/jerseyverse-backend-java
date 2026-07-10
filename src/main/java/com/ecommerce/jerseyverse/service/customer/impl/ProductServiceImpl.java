package com.ecommerce.jerseyverse.service.customer.impl;

import com.ecommerce.jerseyverse.dto.response.PageResponse;
import com.ecommerce.jerseyverse.dto.response.PriceRangeResponse;
import com.ecommerce.jerseyverse.dto.response.Product.ProductSummaryResponse;
import com.ecommerce.jerseyverse.entity.Product;
import com.ecommerce.jerseyverse.exception.InvalidSortOptionException;
import com.ecommerce.jerseyverse.mapper.ProductMapper;
import com.ecommerce.jerseyverse.repository.ProductRepository;
import com.ecommerce.jerseyverse.service.customer.ProductService;
import com.ecommerce.jerseyverse.specification.ProductSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
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

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ProductSummaryResponse> getProducts(String search, BigDecimal minPrice, BigDecimal maxPrice, String sort, Pageable pageable) {

        String keyword = (search == null) ? "" : search.trim();

        Pageable sortedPagable = buildPageable(pageable, sort);

        Specification<Product> specification =
                ProductSpecification.hasName(keyword)
                        .and(ProductSpecification.hasMinimumPrice(minPrice))
                        .and(ProductSpecification.hasMaximumPrice(maxPrice));

        Page<Product> productPage = productRepository.findAll(specification,sortedPagable);

        PageResponse<ProductSummaryResponse> response = new PageResponse<>();

        response.setContent(
                productPage.getContent()
                        .stream()
                        .map(productMapper::toProductSummaryResponse)
                        .toList()
        );

        response.setPage(productPage.getNumber());
        response.setSize(productPage.getSize());
        response.setTotalElements(productPage.getTotalElements());
        response.setTotalPages(productPage.getTotalPages());
        response.setFirst(productPage.isFirst());
        response.setLast(productPage.isLast());

        return response;
    }

    private Pageable buildPageable(Pageable pageable, String sort) {

        if (sort == null || sort.isBlank()) {
            return PageRequest.of(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    Sort.by("createdAt").descending()
            );
        }

        String sortOption = sort.trim().toLowerCase();

        Sort sortBy = switch (sortOption) {

            case "price,asc" ->
                    Sort.by("price").ascending();

            case "price,desc" ->
                    Sort.by("price").descending();

            case "name,asc" ->
                    Sort.by("name").ascending();

            case "name,desc" ->
                    Sort.by("name").descending();

            default ->
                    throw new InvalidSortOptionException(
                            "Invalid sort option: " + sort
                    );
        };

        return PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                sortBy
        );
    }

    @Override
    @Transactional(readOnly = true)
    public PriceRangeResponse getPriceRange() {

        PriceRangeResponse response = new PriceRangeResponse();

        response.setMinPrice(productRepository.findMinimumPrice());
        response.setMaxPrice(productRepository.findMaximumPrice());

        return response;
    }

}
