package com.projetko.stockmanagement.product;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String name,
        String description,
        String sku,
        Integer quantity,
        BigDecimal price,
        Long categoryId,
        String categoryName
        // ny eo ambany an de de type io eto ambony io
) {
    public static ProductResponse fromEntity(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getSku(),
                product.getQuantity(),
                product.getPrice(),
                product.getCategory().getId(),
                product.getCategory().getName()
        );
    }
}
