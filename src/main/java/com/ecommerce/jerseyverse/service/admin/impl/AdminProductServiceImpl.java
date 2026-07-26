package com.ecommerce.jerseyverse.service.admin.impl;

import com.ecommerce.jerseyverse.dto.request.product.CreateProductRequest;
import com.ecommerce.jerseyverse.dto.request.product.ProductVariantRequest;
import com.ecommerce.jerseyverse.dto.request.product.UpdateProductRequest;
import com.ecommerce.jerseyverse.dto.response.Product.AdminProductDetailResponse;
import com.ecommerce.jerseyverse.dto.response.Product.AdminProductResponse;
import com.ecommerce.jerseyverse.entity.Category;


import com.ecommerce.jerseyverse.entity.Product;
import com.ecommerce.jerseyverse.entity.ProductVariant;
import com.ecommerce.jerseyverse.enums.Size;
import com.ecommerce.jerseyverse.exception.InvalidProductVariantException;
import com.ecommerce.jerseyverse.exception.ResourceAlreadyExistsException;
import com.ecommerce.jerseyverse.exception.ResourceNotFoundException;
import com.ecommerce.jerseyverse.mapper.ProductMapper;
import com.ecommerce.jerseyverse.repository.CategoryRepository;
import com.ecommerce.jerseyverse.repository.ProductRepository;
import com.ecommerce.jerseyverse.repository.ProductVariantRepository;
import com.ecommerce.jerseyverse.service.ImageStorageService;
import com.ecommerce.jerseyverse.service.admin.AdminProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Transactional
public class AdminProductServiceImpl implements AdminProductService {

    private static final Logger logger =
            LoggerFactory.getLogger(AdminProductServiceImpl.class);

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    private final ProductVariantRepository productVariantRepository;
    private final ImageStorageService imageStorageService;

    public AdminProductServiceImpl(
            ProductRepository productRepository,
            CategoryRepository categoryRepository,
            ProductMapper productMapper, ProductVariantRepository productVariantRepository, ImageStorageService imageStorageService
    ) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
        this.productVariantRepository = productVariantRepository;
        this.imageStorageService = imageStorageService;
    }

    @Override
    @Transactional
    public AdminProductResponse createProduct(
            CreateProductRequest request,
            MultipartFile image) {

        validateProductName(request.getName());

        Category category = getRequiredCategory(request.getCategoryId());

        String imageUrl = imageStorageService.storeProductImage(image);

        try {
            Product product = productMapper.toEntity(request);

            product.setImageUrl(imageUrl);
            product.setCategory(category);

            Product savedProduct = productRepository.saveAndFlush(product);

            return productMapper.toAdminProductResponse(savedProduct);

        } catch (Exception e) {

            try {
                imageStorageService.deleteProductImage(imageUrl);
            } catch (Exception cleanupException) {
                logger.error(
                        "Failed to clean up product image after product creation failure: {}",
                        imageUrl,
                        cleanupException
                );
            }

            throw e;
        }
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

        ensureUniqueProductName(request.getName(), productId);

        Category category = getRequiredCategory(request.getCategoryId());

        productMapper.updateEntity(product, request);

        product.setCategory(category);

//        product.getVariants().clear();
//
//        productRepository.saveAndFlush(product);
//
//        replaceProductVariants(product, request.getVariants());

        syncProductVariants(
                product,
                request.getVariants()
        );

        Product updatedProduct = productRepository.save(product);

        return productMapper.toAdminProductDetailResponse(updatedProduct);
    }

    private void syncProductVariants(
            Product product,
            List<ProductVariantRequest> requestedVariants
    ) {

        Map<Size, ProductVariant> existingVariants =
                product.getVariants()
                        .stream()
                        .collect(Collectors.toMap(
                                ProductVariant::getSize,
                                Function.identity()
                        ));

        Set<Size> requestedSizes = new HashSet<>();

        for (ProductVariantRequest requestVariant : requestedVariants) {

            Size size = requestVariant.getSize();

            requestedSizes.add(size);

            ProductVariant existingVariant =
                    existingVariants.get(size);

            if (existingVariant != null) {

                existingVariant.setStock(
                        requestVariant.getStock()
                );

            } else {

                ProductVariant newVariant =
                        new ProductVariant();

                newVariant.setSize(size);
                newVariant.setStock(
                        requestVariant.getStock()
                );
                newVariant.setProduct(product);

                product.getVariants().add(newVariant);
            }
        }

        product.getVariants()
                .stream()
                .filter(variant ->
                        !requestedSizes.contains(
                                variant.getSize()
                        )
                )
                .forEach(variant ->
                        variant.setStock(0)
                );
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

    private void validateDuplicateSizes(
            List<ProductVariantRequest> requests) {

        Set<Size> sizes = new HashSet<>();

        for(ProductVariantRequest request : requests){
            if(!sizes.add(request.getSize())){
                throw new InvalidProductVariantException(
                        "Duplicate size '" + request.getSize() + "' is not allowed."
                );
            }
        }

        }
    }




