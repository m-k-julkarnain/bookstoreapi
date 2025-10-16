package com.roshogolla.bookstoreapi.repository;

import com.roshogolla.bookstoreapi.entity.Author;
import com.roshogolla.bookstoreapi.entity.Book;
import com.roshogolla.bookstoreapi.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BookRepositoryTests {
    @Autowired
    BookRepository bookRepository;

    @Test
    public void testFindById() {
        Author author = Author.builder().name("Polin").bio("Bangladeshi Writer").build();
        Category category = Category.builder().name("War").build();

        Book book = Book.builder()
                .title("The Hammer of Iron")
                .author(author)
                .category(category)
                .price(799.0F)
                .publishedDate(LocalDate.now())
                .build();

        Book savedBook = bookRepository.save(book);

        Optional<Book> foundBook = bookRepository.findById(savedBook.getId());

        assertThat(foundBook).isNotEmpty();
        assertThat(foundBook.get().getTitle()).isEqualTo("The Hammer of Iron");
        assertThat(foundBook.get().getAuthor().getName()).isEqualTo("Polin");
    }

    @Test
    public void testFindByTitle() {
        Author author = Author.builder().name("Polin").bio("Bangladeshi Writer").build();
        Category category = Category.builder().name("War").build();

        Book book = Book.builder()
                .title("The Hammer of Iron")
                .author(author)
                .category(category)
                .price(799.0F)
                .publishedDate(LocalDate.now())
                .build();

        bookRepository.save(book);

        List<Book> foundBook = bookRepository.findByTitle("The Hammer of Iron");

        assertThat(foundBook).isNotEmpty();
        assertThat(foundBook.get(0).getTitle()).isEqualTo("The Hammer of Iron");
        assertThat(foundBook.get(0).getAuthor().getName()).isEqualTo("Polin");
    }

    @Test
    public void testFindByPriceGreaterThan() {
        Author author = Author.builder().name("Polin").bio("Bangladeshi Writer").build();
        Category category = Category.builder().name("War").build();

        Book book = Book.builder()
                .title("The Hammer of Iron")
                .author(author)
                .category(category)
                .price(799.0F)
                .publishedDate(LocalDate.now())
                .build();

        bookRepository.save(book);

        List<Book> foundBook = bookRepository.findByPriceGreaterThan(500F);

        assertThat(foundBook).isNotEmpty();
        assertThat(foundBook.get(0).getTitle()).isEqualTo("The Hammer of Iron");
        assertThat(foundBook.get(0).getAuthor().getName()).isEqualTo("Polin");
    }

    @Test
    public void testFindByPriceLessThan() {
        Author author = Author.builder().name("Polin").bio("Bangladeshi Writer").build();
        Category category = Category.builder().name("War").build();

        Book book = Book.builder()
                .title("The Hammer of Iron")
                .author(author)
                .category(category)
                .price(799.0F)
                .publishedDate(LocalDate.now())
                .build();

        bookRepository.save(book);

        List<Book> foundBook = bookRepository.findByPriceLessThan(1000F);

        assertThat(foundBook).isNotEmpty();
        assertThat(foundBook.get(0).getTitle()).isEqualTo("The Hammer of Iron");
        assertThat(foundBook.get(0).getAuthor().getName()).isEqualTo("Polin");
    }

    @Test
    public void testUpdateBook() {
        Author author = Author.builder().name("Polin").bio("Bangladeshi Writer").build();
        Category category = Category.builder().name("War").build();

        Book book = Book.builder()
                .title("The Hammer of Iron")
                .author(author)
                .category(category)
                .price(799.0F)
                .publishedDate(LocalDate.now())
                .build();

        Book savedBook = bookRepository.save(book);

        savedBook.setTitle("The Hammer of Rust");
        savedBook.setPrice(899.0F);

        Optional<Book> foundBook = bookRepository.findById(savedBook.getId());

        assertThat(foundBook).isNotEmpty();
        assertThat(foundBook.get().getTitle()).isEqualTo("The Hammer of Rust");
        assertThat(foundBook.get().getAuthor().getName()).isEqualTo("Polin");
        assertThat(foundBook.get().getPrice()).isEqualTo(899.0F);
    }


}
