package com.projetko.stockmanagement.supplier;

public record SupplierResponse(
        Long id,
        String name,
        String email,
        String phone,
        String address
) {
    public static SupplierResponse fromEntity(Supplier supplier) {
        return new SupplierResponse(
                supplier.getId(),
                supplier.getName(),
                supplier.getEmail(),
                supplier.getPhone(),
                supplier.getAddress()
        );
    }
}