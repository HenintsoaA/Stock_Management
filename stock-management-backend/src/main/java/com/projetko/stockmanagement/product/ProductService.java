package com.projetko.stockmanagement.product;

import com.projetko.stockmanagement.category.Category;
import com.projetko.stockmanagement.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductResponse findById(Long id) {
        Product product = getProductById(id);
        return ProductResponse.fromEntity(product);
    }

    public ProductResponse create(ProductRequest request) {
        if (productRepository.existsBySku(request.sku())) {
            throw new RuntimeException("Product sku already exists");
        }

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = Product.builder()
                .name(request.name())
                .description(request.description())
                .sku(request.sku())
                .quantity(request.quantity())
                .price(request.price())
                .category(category)
                .build();

        Product savedProduct = productRepository.save(product);
        return ProductResponse.fromEntity(savedProduct);
    }

    public ProductResponse update(Long id, ProductRequest request) {
        Product product = getProductById(id);

        productRepository.findBySku(request.sku())
                .filter(existingProduct -> !existingProduct.getId().equals(id)) // cette ligne cherche si un autre produit que l'on modifie maintenant utilise deja ce sku
                .ifPresent(existingProduct -> {
                    throw new RuntimeException("SKU already exists");
                });
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.setName(request.name());
        product.setDescription(request.description());
        product.setSku(request.sku());
        product.setQuantity(request.quantity());
        product.setPrice(request.price());
        product.setCategory(category);

        Product updatedProduct = productRepository.save(product);
        return ProductResponse.fromEntity(updatedProduct);
    }

    public void delete(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }

    private Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }
}
