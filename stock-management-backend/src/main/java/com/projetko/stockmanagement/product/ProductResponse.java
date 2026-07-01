package com.projetko.stockmanagement.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponse(
        Long id,
        String name,
        String description,
        String sku,
        Integer quantity,
        BigDecimal price,
        Long categoryId,
        String categoryName,
        Long supplierId,
        String supplierName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
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
                product.getCategory().getName(),
                product.getSupplier() != null ? product.getSupplier().getId() : null,
                product.getSupplier() != null ? product.getSupplier().getName() : null,
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}
