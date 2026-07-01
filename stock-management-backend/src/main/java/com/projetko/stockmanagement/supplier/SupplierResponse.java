package com.projetko.stockmanagement.supplier;

import java.time.LocalDateTime;

public record SupplierResponse(
        Long id,
        String name,
        String email,
        String phone,
        String address,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static SupplierResponse fromEntity(Supplier supplier) {
        return new SupplierResponse(
                supplier.getId(),
                supplier.getName(),
                supplier.getEmail(),
                supplier.getPhone(),
                supplier.getAddress(),
                supplier.getCreatedAt(),
                supplier.getUpdatedAt()
        );
    }
}