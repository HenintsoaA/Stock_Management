package com.projetko.stockmanagement.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    boolean existsBySku(String sku);

    Optional<Product> findBySku(String sku);

    long countByQuantityLessThanEqual(Integer quantity);
}