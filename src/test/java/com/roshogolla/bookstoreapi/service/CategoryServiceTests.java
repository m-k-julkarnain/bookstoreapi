package com.roshogolla.bookstoreapi.service;

import com.roshogolla.bookstoreapi.entity.Category;
import com.roshogolla.bookstoreapi.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTests {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    public void testAddCategory() {
        Category category = Category.builder().id(1L).name("War").build();

        when(categoryRepository.save(category)).thenReturn(category);

        Category savedCategory = categoryService.addCategory(category);

        assertThat(savedCategory).isNotNull();
        assertThat(savedCategory.getName()).isEqualTo("War");
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    public void testFindCategoryById() {
        Category category = Category.builder().id(1L).name("War").build();

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        Optional<Category> foundCategory = categoryService.getCategoryById(1L);

        assertThat(foundCategory).isNotNull();
        assertThat(foundCategory.get().getName()).isEqualTo("War");
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindCategoryByName() {
        Category category = Category.builder().id(1L).name("War").build();

        when(categoryRepository.findByNameIgnoreCase("war")).thenReturn(List.of(category));

        List<Category> foundCategory = categoryService.searchCategoryByName("war");

        assertThat(foundCategory).isNotNull();
        assertThat(foundCategory.get(0).getName()).isEqualTo("War");
        verify(categoryRepository, times(1)).findByNameIgnoreCase("war");
    }

    @Test
    public void testGetAllCategory() {
        Category category = Category.builder().id(1L).name("War").build();

        when(categoryRepository.findAll()).thenReturn(List.of(category));

        List<Category> foundCategory = categoryService.getAllCategories();

        assertThat(foundCategory).isNotNull();
        assertThat(foundCategory.get(0).getName()).isEqualTo("War");
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    public void testUpdateCategory() {
        Category category = Category.builder().id(1L).name("War").build();
        Category updatedCategory = Category.builder().id(1L).name("Romance").build();

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.save(category)).thenReturn(updatedCategory);

        Category foundCategory = categoryService.updateCategory(1L, updatedCategory);

        assertThat(foundCategory).isNotNull();
        assertThat(foundCategory.getName()).isEqualTo("Romance");
        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    public void testDeleteCategory() {
        Category category = Category.builder().id(1L).name("War").build();

        categoryService.deleteCategory(1L);

        verify(categoryRepository, times(1)).deleteById(1L);
    }
}


















