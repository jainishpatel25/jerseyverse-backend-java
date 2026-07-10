package com.ecommerce.jerseyverse.specification;

import com.ecommerce.jerseyverse.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductSpecification {
    private ProductSpecification() {
    }

    public static Specification<Product> hasName(String keyword) {

        return (root, query, criteriaBuilder) -> {

            if (keyword == null || keyword.isBlank()) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")),
                    "%" + keyword.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Product> hasMinimumPrice(BigDecimal minPrice) {

        return (root, query, criteriaBuilder) -> {

            if (minPrice == null) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.greaterThanOrEqualTo(
                    root.get("price"),
                    minPrice
            );
        };
    }

    public static Specification<Product> hasMaximumPrice(BigDecimal maxPrice) {

        return (root, query, criteriaBuilder) -> {

            if (maxPrice == null) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.lessThanOrEqualTo(
                    root.get("price"),
                    maxPrice
            );
        };
    }

}
