package com.roshogolla.bookstoreapi.service;

import com.roshogolla.bookstoreapi.entity.Category;
import com.roshogolla.bookstoreapi.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    //Create
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    //Read category by ID
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    //Search category by category name
    public List<Category> searchCategoryByName(String name) {
        return categoryRepository.findByNameIgnoreCase(name);
    }

    //Read all category
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    //Update category
    public Category updateCategory(Long id, Category updatedCategory) {
        return categoryRepository.findById(id)
            .map(category -> {
                category.setName(updatedCategory.getName());
                return categoryRepository.save(category);
            })
            .orElseThrow(() -> new RuntimeException("Category not found with id " + id));
    }

    //Delete
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
