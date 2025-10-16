package com.roshogolla.bookstoreapi.controller;

import com.roshogolla.bookstoreapi.dto.category.CategoryCreateDTO;
import com.roshogolla.bookstoreapi.dto.category.CategoryResponseDTO;
import com.roshogolla.bookstoreapi.dto.category.CategoryUpdateDTO;
import com.roshogolla.bookstoreapi.entity.Category;
import com.roshogolla.bookstoreapi.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //Create new category
    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@Valid @RequestBody CategoryCreateDTO categoryCreateDTO) {
        Category category = new Category();
        category.setName(categoryCreateDTO.getName());

        Category savedCategory = categoryService.addCategory(category);

        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO(
                savedCategory.getId(),
                savedCategory.getName()
        );

        return ResponseEntity.ok(categoryResponseDTO);
    }

    //Get category by id
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id " + id));

        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO(
                category.getId(),
                category.getName()
        );

        return ResponseEntity.ok(categoryResponseDTO);
    }

    //Search category by name
    @GetMapping("/search/by-name")
    public ResponseEntity<List<CategoryResponseDTO>> getCategoryByName(@RequestParam String name) {
        List<CategoryResponseDTO> categories= categoryService.searchCategoryByName(name)
                .stream()
                .map(category -> new CategoryResponseDTO(
                        category.getId(),
                        category.getName()
                )).collect(Collectors.toList());
        return ResponseEntity.ok(categories);
    }

    //Get all categories
    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
        List<CategoryResponseDTO> categories = categoryService.getAllCategories()
                .stream()
                .map(category -> new CategoryResponseDTO(
                        category.getId(),
                        category.getName()
                )).collect(Collectors.toList());
        return ResponseEntity.ok(categories);
    }

    //Update category
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryUpdateDTO categoryUpdateDTO) {
        Category updatedCategory = new Category();
        updatedCategory.setId(id);
        updatedCategory.setName(categoryUpdateDTO.getName());

        Category savedCategory = categoryService.updateCategory(id, updatedCategory);

        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO(
                savedCategory.getId(),
                savedCategory.getName()
        );
        return ResponseEntity.ok(categoryResponseDTO);
    }

    //Delete Category
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Category deleted successfully");
    }
}


















