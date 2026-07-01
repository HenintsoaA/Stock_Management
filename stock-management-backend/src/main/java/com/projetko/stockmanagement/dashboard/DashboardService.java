package com.projetko.stockmanagement.dashboard;

import com.projetko.stockmanagement.category.CategoryRepository;
import com.projetko.stockmanagement.product.Product;
import com.projetko.stockmanagement.product.ProductRepository;
import com.projetko.stockmanagement.stockmovement.StockMovementRepository;
import com.projetko.stockmanagement.stockmovement.StockMovementResponse;
import com.projetko.stockmanagement.supplier.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardService {

    private static final int LOW_STOCK_THRESHOLD = 5;

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final StockMovementRepository stockMovementRepository;

    public DashboardResponse getDashboard() {
        List<Product> products = productRepository.findAll();

        BigDecimal totalStockValue = products.stream()
                .map(product -> product.getPrice().multiply(BigDecimal.valueOf(product.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<StockMovementResponse> recentMovements = stockMovementRepository.findTop5ByOrderByMovementDateDesc()
                .stream()
                .map(StockMovementResponse::fromEntity)
                .toList();

        return new DashboardResponse(
                productRepository.count(),
                categoryRepository.count(),
                supplierRepository.count(),
                productRepository.countByQuantityLessThanEqual(LOW_STOCK_THRESHOLD),
                totalStockValue,
                recentMovements
        );
    }
}
