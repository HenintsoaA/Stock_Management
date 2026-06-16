package com.projetko.stockmanagement.stockmovement;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record StockMovementRequest(
        @NotNull(message = "Movement type is required")
        StockMovementType type,

        @NotNull(message = "Quantity is required")
        @Positive(message = "Qantity must be greater than zero")
        Integer quantity,

        String reason,

        @NotNull(message = "Product is required")
        Long productId
) {
}
