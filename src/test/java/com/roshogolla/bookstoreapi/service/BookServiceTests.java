package com.roshogolla.bookstoreapi.service;

import com.roshogolla.bookstoreapi.entity.Author;
import com.roshogolla.bookstoreapi.entity.Book;
import com.roshogolla.bookstoreapi.entity.Category;
import com.roshogolla.bookstoreapi.repository.BookRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BookServiceTests {
    @Mock
    private BookRepository bookRepository;;

    @InjectMocks
    private BookService bookService;

    @Test
    public void testAddBook() {
        Author author = Author.builder().id(1L).name("Nilop").bio("I love animal").build();
        Category category = Category.builder().id(1L).name("War").build();

        Book book = Book.builder()
                .title("The Hammer of Iron")
                .author(author)
                .category(category)
                .price(799F)
                .publishedDate(LocalDate.now())
                .build();

        when(bookRepository.save(book)).thenReturn(book);

        Book savedBook = bookService.addBook(book);

        assertThat(savedBook).isNotNull();
        assertThat(savedBook.getTitle()).isEqualTo("The Hammer of Iron");
        assertThat(savedBook.getAuthor().getName()).isEqualTo("Nilop");
        assertThat(savedBook.getPrice()).isEqualTo(799F);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    public void testFindBookById() {
        Author author = Author.builder().id(1L).name("Nilop").bio("I love animal").build();
        Category category = Category.builder().name("War").build();

        Book book = Book.builder()
                .id(1L)
                .title("The Hammer of Iron")
                .author(author)
                .category(category)
                .price(799F)
                .publishedDate(LocalDate.now())
                .build();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Optional<Book> foundBook = bookService.getBookById(1L);

        assertThat(foundBook).isNotNull();
        assertThat(foundBook.get().getTitle()).isEqualTo("The Hammer of Iron");
        assertThat(foundBook.get().getAuthor().getName()).isEqualTo("Nilop");
        assertThat(foundBook.get().getPrice()).isEqualTo(799F);
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindBookByTitle() {
        Author author = Author.builder().id(1L).name("Nilop").bio("I love animal").build();
        Category category = Category.builder().name("War").build();

        Book book = Book.builder()
                .id(1L)
                .title("The Hammer of Iron")
                .author(author)
                .category(category)
                .price(799F)
                .publishedDate(LocalDate.now())
                .build();

        when(bookRepository.findByTitle("The Hammer of Iron")).thenReturn(List.of(book));

        List<Book> foundBook = bookService.getBookByTitle("The Hammer of Iron");

        assertThat(foundBook).isNotNull();
        assertThat(foundBook.get(0).getTitle()).isEqualTo("The Hammer of Iron");
        assertThat(foundBook.get(0).getAuthor().getName()).isEqualTo("Nilop");
        assertThat(foundBook.get(0).getPrice()).isEqualTo(799F);
        verify(bookRepository, times(1)).findByTitle("The Hammer of Iron");
    }

    @Test
    public void testFindBookByKeyword() {
        Author author = Author.builder().id(1L).name("Nilop").bio("I love animal").build();
        Category category = Category.builder().name("War").build();

        Book book = Book.builder()
                .id(1L)
                .title("The Hammer of Iron")
                .author(author)
                .category(category)
                .price(799F)
                .publishedDate(LocalDate.now())
                .build();

        when(bookRepository.findByTitleContainingIgnoreCase("Iron")).thenReturn(List.of(book));

        List<Book> foundBook = bookService.searchBooksByKeyword("Iron");

        assertThat(foundBook).isNotNull();
        assertThat(foundBook.get(0).getTitle()).isEqualTo("The Hammer of Iron");
        assertThat(foundBook.get(0).getAuthor().getName()).isEqualTo("Nilop");
        assertThat(foundBook.get(0).getPrice()).isEqualTo(799F);
        verify(bookRepository, times(1)).findByTitleContainingIgnoreCase("Iron");
    }

    @Test
    public void testFindBookByPriceLessThan() {
        Author author = Author.builder().id(1L).name("Nilop").bio("I love animal").build();
        Category category = Category.builder().name("War").build();

        Book book = Book.builder()
                .id(1L)
                .title("The Hammer of Iron")
                .author(author)
                .category(category)
                .price(799F)
                .publishedDate(LocalDate.now())
                .build();

        when(bookRepository.findByPriceLessThan(850F)).thenReturn(List.of(book));

        List<Book> foundBook = bookService.searchBooksByPriceLessThan(850F);

        assertThat(foundBook).isNotNull();
        assertThat(foundBook.get(0).getTitle()).isEqualTo("The Hammer of Iron");
        assertThat(foundBook.get(0).getAuthor().getName()).isEqualTo("Nilop");
        assertThat(foundBook.get(0).getPrice()).isEqualTo(799F);
        verify(bookRepository, times(1)).findByPriceLessThan(850F);
    }

    @Test
    public void testFindBookByPriceGreaterThan() {
        Author author = Author.builder().id(1L).name("Nilop").bio("I love animal").build();
        Category category = Category.builder().name("War").build();

        Book book = Book.builder()
                .id(1L)
                .title("The Hammer of Iron")
                .author(author)
                .category(category)
                .price(799F)
                .publishedDate(LocalDate.now())
                .build();

        when(bookRepository.findByPriceGreaterThan(750F)).thenReturn(List.of(book));

        List<Book> foundBook = bookService.searchBooksByPriceGreaterThan(750F);

        assertThat(foundBook).isNotNull();
        assertThat(foundBook.get(0).getTitle()).isEqualTo("The Hammer of Iron");
        assertThat(foundBook.get(0).getAuthor().getName()).isEqualTo("Nilop");
        assertThat(foundBook.get(0).getPrice()).isEqualTo(799F);
        verify(bookRepository, times(1)).findByPriceGreaterThan(750F);
    }

    @Test
    public void testGetAllBooks() {
        Author author = Author.builder().id(1L).name("Nilop").bio("I love animal").build();
        Category category = Category.builder().name("War").build();

        Book book = Book.builder()
                .id(1L)
                .title("The Hammer of Iron")
                .author(author)
                .category(category)
                .price(799F)
                .publishedDate(LocalDate.now())
                .build();

        when(bookRepository.findAll()).thenReturn(List.of(book));

        List<Book> foundBook = bookService.getAllBooks();

        assertThat(foundBook).isNotNull();
        assertThat(foundBook.get(0).getTitle()).isEqualTo("The Hammer of Iron");
        assertThat(foundBook.get(0).getAuthor().getName()).isEqualTo("Nilop");
        assertThat(foundBook.get(0).getPrice()).isEqualTo(799F);
        verify(bookRepository, times(1)).findAll();
    }


    @Test
    public void testUpdateBook() {
        Author author = Author.builder().id(1L).name("Nilop").bio("I love animal").build();
        Category category = Category.builder().name("War").build();

        Book book = Book.builder()
                .id(1L)
                .title("The Hammer of Iron")
                .author(author)
                .category(category)
                .price(799F)
                .publishedDate(LocalDate.now())
                .build();

        Book updatedBook = Book.builder()
                .id(1L)
                .title("The Hammer of Gold")
                .author(author)
                .category(category)
                .price(999F)
                .publishedDate(LocalDate.now())
                .build();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

        Book foundBook = bookService.updateBook(1L, updatedBook);

        assertThat(foundBook).isNotNull();
        assertThat(foundBook.getTitle()).isEqualTo("The Hammer of Gold");
        assertThat(foundBook.getAuthor().getName()).isEqualTo("Nilop");
        assertThat(foundBook.getPrice()).isEqualTo(999F);
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    public void testDeleteBook() {
        Author author = Author.builder().id(1L).name("Nilop").bio("I love animal").build();
        Category category = Category.builder().name("War").build();

        Book book = Book.builder()
                .id(1L)
                .title("The Hammer of Iron")
                .author(author)
                .category(category)
                .price(799F)
                .publishedDate(LocalDate.now())
                .build();

        bookService.deleteBookById(1L);

        verify(bookRepository, times(1)).deleteById(1L);
    }




}











