package com.roshogolla.bookstoreapi.repository;

import com.roshogolla.bookstoreapi.entity.Book;
import com.roshogolla.bookstoreapi.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CategoryRepositoryTests {
    @Autowired
    CategoryRepository categoryRepository;

    @Test
    public void testFindCategoryById() {
        Category category = Category.builder().name("War").build();
        categoryRepository.save(category);

        Optional<Category> foundCategory = categoryRepository.findById(category.getId());

        assertThat(foundCategory).isNotEmpty();
        assertThat(foundCategory.get().getName()).isEqualTo("War");
    }

    @Test
    public void testFindCategoryByName() {
        Category category = Category.builder().name("War").build();
        categoryRepository.save(category);

        List<Category> foundCategory = categoryRepository.findByNameIgnoreCase("war");

        assertThat(foundCategory).isNotEmpty();
        assertThat(foundCategory.get(0).getName()).isEqualTo("War");
    }

    @Test
    public void testUpdateCategory() {
        Category category = Category.builder().name("War").build();
        Category savedCategory = categoryRepository.save(category);

        savedCategory.setName("Romance");

        Optional<Category> foundCategory = categoryRepository.findById(savedCategory.getId());

        assertThat(foundCategory).isNotEmpty();
        assertThat(foundCategory.get().getName()).isEqualTo("Romance");
    }



}
