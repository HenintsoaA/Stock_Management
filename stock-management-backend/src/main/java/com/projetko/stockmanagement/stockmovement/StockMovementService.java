package com.projetko.stockmanagement.stockmovement;

import com.projetko.stockmanagement.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StockMovementService {

    private final StockMovementRepository stockMovementRepository;
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<StockMovementResponse> findAll() {
        return stockMovementRepository.findAll()
                .stream()
                .map(StockMovementResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<StockMovementResponse> findByProductId(Long productId) {
        return stockMovementRepository.findByProductIdOrderByMovementDateDesc(productId)
                .stream()
                .map(StockMovementResponse::fromEntity)
                .toList();
    }
}
