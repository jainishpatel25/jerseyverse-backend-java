package com.ecommerce.jerseyverse.service.admin.impl;

import com.ecommerce.jerseyverse.dto.request.product.CreateProductRequest;
import com.ecommerce.jerseyverse.dto.request.product.UpdateProductRequest;
import com.ecommerce.jerseyverse.dto.response.Product.AdminProductDetailResponse;
import com.ecommerce.jerseyverse.dto.response.Product.AdminProductResponse;
import com.ecommerce.jerseyverse.entity.Category;


import com.ecommerce.jerseyverse.entity.Product;
import com.ecommerce.jerseyverse.exception.ResourceAlreadyExistsException;
import com.ecommerce.jerseyverse.exception.ResourceNotFoundException;
import com.ecommerce.jerseyverse.mapper.ProductMapper;
import com.ecommerce.jerseyverse.repository.CategoryRepository;
import com.ecommerce.jerseyverse.repository.ProductRepository;
import com.ecommerce.jerseyverse.service.admin.AdminProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AdminProductServiceImpl implements AdminProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public AdminProductServiceImpl(
            ProductRepository productRepository,
            CategoryRepository categoryRepository,
            ProductMapper productMapper
    ) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    @Override
    public AdminProductResponse createProduct(CreateProductRequest request) {

        validateProductName(request.getName());

        Category category = getRequiredCategory(request.getCategoryId());

        Product product = productMapper.toEntity(request);

        product.setCategory(category);

        Product savedProduct = productRepository.save(product);

        return productMapper.toAdminProductResponse(savedProduct);
    }

    private void validateProductName(String productName) {

        if (productRepository.existsByName(productName)) {
            throw new ResourceAlreadyExistsException(
                    "Product with name '" + productName + "' already exists."
            );
        }
    }

    private Category getRequiredCategory(Long categoryId) {

        return categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdminProductResponse> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(productMapper::toAdminProductResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AdminProductDetailResponse getProductById(Long productId){

        Product product = getRequiredProduct(productId);

        return productMapper.toAdminProductDetailResponse(product);


    }

    @Override
    public AdminProductDetailResponse updateProduct(Long productId, UpdateProductRequest request) {
        Product product = getRequiredProduct(productId);

        ensureUniqueProductName(
                request.getName(),
                productId
        );

        Category category =
                getRequiredCategory(request.getCategoryId());

        productMapper.updateEntity(product, request);

        product.setCategory(category);

        Product updatedProduct =
                productRepository.save(product);

        return productMapper
                .toAdminProductDetailResponse(updatedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(Long productId) {

        Product product = getRequiredProduct(productId);

        productRepository.delete(product);

    }

    private Product getRequiredProduct(Long productId){

        return productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found."));

    }

    private void ensureUniqueProductName(
            String productName,
            Long productId
    ) {

        if (productRepository.existsByNameAndIdNot(productName, productId)) {

            throw new ResourceAlreadyExistsException(
                    "Product with name '" + productName + "' already exists."
            );
        }
    }





}
