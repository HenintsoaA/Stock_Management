package com.projetko.stockmanagement.supplier;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SupplierRequest (
        @NotBlank(message = "Supplier name is required")
        String name,

        @Email(message = "Email should be valid")
        String email,

        String phone,

        String address
) {
}
