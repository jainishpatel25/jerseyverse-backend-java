package com.ecommerce.jerseyverse.service.admin;

import com.ecommerce.jerseyverse.dto.response.Product.CategoryResponse;

import java.util.List;

public interface CategoryService {

    List<CategoryResponse> getAllCategories();

}
