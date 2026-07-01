package com.projetko.stockmanagement.stockmovement;

import com.projetko.stockmanagement.common.BaseEntity;
import com.projetko.stockmanagement.product.Product;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "stock_movements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockMovement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StockMovementType type;

    @Column(nullable = false)
    private Integer quantity;

    private String reason;

    @Column(nullable = false)
    private LocalDateTime movementDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
