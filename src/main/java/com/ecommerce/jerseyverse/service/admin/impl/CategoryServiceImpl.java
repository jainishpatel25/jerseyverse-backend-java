package com.ecommerce.jerseyverse.service.admin.impl;

import com.ecommerce.jerseyverse.dto.response.Product.CategoryResponse;
import com.ecommerce.jerseyverse.repository.CategoryRepository;
import com.ecommerce.jerseyverse.service.admin.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(
            CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories() {

        return categoryRepository.findAll()
                .stream()
                .map(category -> {
                    CategoryResponse response =
                            new CategoryResponse();

                    response.setId(category.getId());
                    response.setName(category.getName());

                    return response;
                })
                .toList();
    }
}
