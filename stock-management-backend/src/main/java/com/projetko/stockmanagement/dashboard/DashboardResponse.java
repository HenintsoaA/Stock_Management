package com.projetko.stockmanagement.dashboard;

import com.projetko.stockmanagement.stockmovement.StockMovementResponse;

import java.math.BigDecimal;
import java.util.List;

public record DashboardResponse(
        long totalProducts,
        long totalCategories,
        long totalSuppliers,
        long lowStockageProducts,
        BigDecimal totalStockValue,
        List<StockMovementResponse> recentMovements
) {
}
