package com.projetko.stockmanagement.category;

import com.projetko.stockmanagement.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryResponse::fromEntity)
                .toList();
    }

    public CategoryResponse findById(Long id) {
        Category category = getCategoryById(id);
        return CategoryResponse.fromEntity(category);
    }

    public CategoryResponse create(CategoryRequest request) {
        Category category = Category.builder()
                .name(request.name())
                .description(request.description())
                .build();

        Category savedCategory = categoryRepository.save(category);
        return CategoryResponse.fromEntity(savedCategory);
    }

    public CategoryResponse update(Long id, CategoryRequest request) {
        Category category = getCategoryById(id);

        category.setName(request.name());
        category.setDescription(request.description());

        Category updatedCategory = categoryRepository.save(category);
        return CategoryResponse.fromEntity(updatedCategory);
    }

    public void delete(Long id) {
        Category category = getCategoryById(id);
        categoryRepository.delete(category);
    }

    private Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }
}
