package com.projetko.stockmanagement.stockmovement;

import java.time.LocalDateTime;

public record StockMovementResponse(
        Long id,
        StockMovementType type,
        Integer quantity,
        String reason,
        LocalDateTime movementDate,
        Long productId,
        String productName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static StockMovementResponse fromEntity(StockMovement movement) {
        return new StockMovementResponse(
                movement.getId(),
                movement.getType(),
                movement.getQuantity(),
                movement.getReason(),
                movement.getMovementDate(),
                movement.getProduct().getId(),
                movement.getProduct().getName(),
                movement.getCreatedAt(),
                movement.getUpdatedAt()
        );
    }
}
