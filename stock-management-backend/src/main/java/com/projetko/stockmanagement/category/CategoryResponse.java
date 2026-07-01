package com.projetko.stockmanagement.category;

import java.time.LocalDateTime;

public record CategoryResponse (
        Long id,
        String name,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static CategoryResponse fromEntity(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }
}
