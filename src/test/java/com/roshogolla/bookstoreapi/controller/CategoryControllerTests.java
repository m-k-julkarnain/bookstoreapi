package com.roshogolla.bookstoreapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roshogolla.bookstoreapi.dto.category.CategoryCreateDTO;
import com.roshogolla.bookstoreapi.dto.category.CategoryUpdateDTO;
import com.roshogolla.bookstoreapi.entity.Category;
import com.roshogolla.bookstoreapi.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CategoryService categoryService;

    @Test
    public void testCreateCategory() throws Exception{
        CategoryCreateDTO categoryCreateDTO = new CategoryCreateDTO();
        categoryCreateDTO.setName("War");

        Category category = Category.builder().id(1L).name("War").build();

        when(categoryService.addCategory(any(Category.class))).thenReturn(category);

        mockMvc.perform(post("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryCreateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("War"));
    }

    @Test
    public void testGetCategoryById() throws Exception{
        Category category = Category.builder().id(1L).name("War").build();

        when(categoryService.getCategoryById(1L)).thenReturn(Optional.of(category));

        mockMvc.perform(get("/category/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("War"));
    }

    @Test
    public void testGetCategoryByName() throws Exception{
        Category category = Category.builder().id(1L).name("War").build();

        when(categoryService.searchCategoryByName("War")).thenReturn(List.of(category));

        mockMvc.perform(get("/category/search/by-name")
                        .param("name", "War")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("War"));
    }

    @Test
    public void testGetAllCategory() throws Exception{
        Category category = Category.builder().id(1L).name("War").build();

        when(categoryService.getAllCategories()).thenReturn(List.of(category));

        mockMvc.perform(get("/category")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("War"));
    }

    @Test
    public void testUpdateCategory() throws Exception{
        Category category = Category.builder().id(1L).name("War").build();

        CategoryUpdateDTO categoryUpdateDTO = new CategoryUpdateDTO();
        categoryUpdateDTO.setId(1L);
        categoryUpdateDTO.setName("Romance");
        Category upadatedCategory = Category.builder().id(1L).name("Romance").build();

        when(categoryService.getCategoryById(1L)).thenReturn(Optional.of(category));
        when(categoryService.updateCategory(eq(1L), any(Category.class))).thenReturn(upadatedCategory);

        mockMvc.perform(put("/category/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryUpdateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Romance"));
    }

    @Test
    public void testDeleteCategory() throws Exception {
        doNothing().when(categoryService).deleteCategory(1L);

        mockMvc.perform(delete("/category/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Category deleted successfully"));
        verify(categoryService, times(1)).deleteCategory(1L);
    }
}

















