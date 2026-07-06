package com.ecommerce.jerseyverse.config;

import com.ecommerce.jerseyverse.entity.Category;
import com.ecommerce.jerseyverse.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryDataSeeder implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    public CategoryDataSeeder(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) {

        if (categoryRepository.count() > 0) {
            return;
        }

        List<String> categoryNames = List.of(
                "Jerseys",
                "Shoes",
                "Accessories",
                "Training Wear"
        );

        for (String categoryName : categoryNames) {
            Category category = new Category();
            category.setName(categoryName);
            categoryRepository.save(category);
        }
    }
}