package com.projetko.stockmanagement.product;

import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    private ProductSpecification() {

    }

    public static Specification<Product> hasKeyword(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if (keyword == null || keyword.isBlank()) {
                return criteriaBuilder.conjunction();
            }

            String pattern = "%" + keyword.toLowerCase() + "%";

            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), pattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("sku")), pattern)
            );
        };
    }


    public static Specification<Product> hasCategory(Long categoryId) {
        return ((root, query, criteriaBuilder) -> {
            if (categoryId == null) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.equal(root.get("category").get("id"), categoryId);
        });
    }

    public static Specification<Product> hasSupplier(Long supplierId) {
        return ((root, query, criteriaBuilder) ->  {
            if (supplierId == null) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.equal(root.get("supplier").get("id"), supplierId);
        });
    }
}
