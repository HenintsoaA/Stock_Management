package com.projetko.stockmanagement.common.exception;

public record ErrorResponse(
        java.time.LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path
) {

}
