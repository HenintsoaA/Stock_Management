package com.projetko.stockmanagement.stockmovement;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock-movements")
@RequiredArgsConstructor
public class StockMovementController {

    private final StockMovementService stockMovementService;

    @GetMapping
    public List<StockMovementResponse> findAll() {
        return stockMovementService.findAll();
    }

    @GetMapping("/product/{productId}")
    public List<StockMovementResponse> findByProduct(@PathVariable Long productId) {
        return stockMovementService.findByProductId(productId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StockMovementResponse create(@Valid @RequestBody StockMovementRequest request){
        return stockMovementService.create(request);
    }
}
