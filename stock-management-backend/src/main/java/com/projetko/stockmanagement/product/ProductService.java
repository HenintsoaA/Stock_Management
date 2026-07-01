package com.projetko.stockmanagement.product;

import com.projetko.stockmanagement.category.Category;
import com.projetko.stockmanagement.category.CategoryRepository;
import com.projetko.stockmanagement.common.PageResponse;
import com.projetko.stockmanagement.common.exception.DuplicateResourceException;
import com.projetko.stockmanagement.common.exception.ResourceNotFoundException;
import com.projetko.stockmanagement.supplier.Supplier;
import com.projetko.stockmanagement.supplier.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;

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
            throw new DuplicateResourceException("Product sku already exists");
        }

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Supplier supplier = request.supplierId() == null
                ? null
                : supplierRepository.findById(request.supplierId())
                        .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

        Product product = Product.builder()
                .name(request.name())
                .description(request.description())
                .sku(request.sku())
                .quantity(request.quantity())
                .price(request.price())
                .category(category)
                .supplier(supplier)
                .build();

        Product savedProduct = productRepository.save(product);
        return ProductResponse.fromEntity(savedProduct);
    }

    public ProductResponse update(Long id, ProductRequest request) {
        Product product = getProductById(id);

        productRepository.findBySku(request.sku())
                .filter(existingProduct -> !existingProduct.getId().equals(id)) // cette ligne cherche si un autre produit que l'on modifie maintenant utilise deja ce sku
                .ifPresent(existingProduct -> {
                    throw new DuplicateResourceException("SKU already exists");
                });
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Supplier supplier = request.supplierId() == null
                ? null
                : supplierRepository.findById(request.supplierId())
                        .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

        product.setName(request.name());
        product.setDescription(request.description());
        product.setSku(request.sku());
        product.setQuantity(request.quantity());
        product.setPrice(request.price());
        product.setCategory(category);
        product.setSupplier(supplier);

        Product updatedProduct = productRepository.save(product);
        return ProductResponse.fromEntity(updatedProduct);
    }

    public void delete(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }

    private Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    @Transactional(readOnly = true)
    public PageResponse<ProductResponse> search(
            int page,
            int size,
            String sortBy,
            String direction,
            String keyword,
            Long categoryId,
            Long supplierId
    ) {
        Sort sort = direction.equalsIgnoreCase("desc")
        ? Sort.by(sortBy).descending()
        : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Product> specification = Specification
                .where(ProductSpecification.hasKeyword(keyword))
                .and(ProductSpecification.hasCategory(categoryId))
                .and(ProductSpecification.hasSupplier(supplierId));

        Page<Product> productPage = productRepository.findAll(specification, pageable);

        List<ProductResponse> content = productPage.getContent()
                .stream()
                .map(ProductResponse::fromEntity)
                .toList();

        return new PageResponse<>(
                content,
                productPage.getNumber(),
                productPage.getSize(),
                productPage.getTotalElements(),
                productPage.getTotalPages(),
                productPage.isFirst(),
                productPage.isLast()
        );

    }
}
