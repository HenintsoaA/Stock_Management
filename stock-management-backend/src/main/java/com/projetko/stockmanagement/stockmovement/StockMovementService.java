package com.projetko.stockmanagement.stockmovement;

import com.projetko.stockmanagement.product.Product;
import com.projetko.stockmanagement.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    public StockMovementResponse create(StockMovementRequest request){
        // verifie si ce produit existe deja pour le mettre ajour car c'est un objet de StockMovement
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (request.type() == StockMovementType.OUT && product.getQuantity() < request.quantity()) {
            throw new RuntimeException("Insufficient quantity");
        }

        if (request.type() == StockMovementType.IN) {
            product.setQuantity(product.getQuantity() + request.quantity());
        } else {
            product.setQuantity(product.getQuantity() - request.quantity());
        }

        StockMovement movement = StockMovement.builder()
                .type(request.type())
                .quantity(request.quantity())
                .reason(request.reason())
                .movementDate(LocalDateTime.now())
                .product(product)
                .build();

        StockMovement savedMovement = stockMovementRepository.save(movement);
        productRepository.save(product);

        return StockMovementResponse.fromEntity(savedMovement);
    }
}
