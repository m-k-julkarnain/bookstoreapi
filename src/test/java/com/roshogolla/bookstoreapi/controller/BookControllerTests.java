package com.roshogolla.bookstoreapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roshogolla.bookstoreapi.dto.book.BookCreateDTO;
import com.roshogolla.bookstoreapi.dto.book.BookUpdateDTO;
import com.roshogolla.bookstoreapi.entity.Author;
import com.roshogolla.bookstoreapi.entity.Book;
import com.roshogolla.bookstoreapi.entity.Category;
import com.roshogolla.bookstoreapi.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
public class BookControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private BookService bookService;

    @Test
    public void testCreateBook() throws Exception{
        Author author = Author.builder().id(1L).name("Polin").build();
        Category category = Category.builder().id(1L).name("War").build();

        BookCreateDTO bookCreateDTO = new BookCreateDTO();
        bookCreateDTO.setTitle("The Hammer of Iron");
        bookCreateDTO.setAuthorName(author);
        bookCreateDTO.setCategoryName(category);
        bookCreateDTO.setPrice(799F);
        bookCreateDTO.setPublishedDate(LocalDate.now());

        Book book = Book.builder().id(1L).title("The Hammer of Iron").price(799F).publishedDate(LocalDate.now()).author(author).category(category).build();

        when(bookService.addBook(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookCreateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("The Hammer of Iron"))
                .andExpect(jsonPath("$.authorName").value("Polin"))
                .andExpect(jsonPath("$.categoryName").value("War"))
                .andExpect(jsonPath("$.price").value(799F))
                .andExpect(jsonPath("$.publishedDate").value(LocalDate.now().toString()));
    }

    @Test
    public void testGetBookById() throws Exception {
        Author author = Author.builder().id(1L).name("Polin").build();
        Category category = Category.builder().id(1L).name("War").build();
        Book book = Book.builder().id(1L).title("The Hammer of Iron").price(799F).publishedDate(LocalDate.now()).author(author).category(category).build();

        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));

        mockMvc.perform(get("/book/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("The Hammer of Iron"))
                .andExpect(jsonPath("$.authorName").value("Polin"))
                .andExpect(jsonPath("$.categoryName").value("War"))
                .andExpect(jsonPath("$.price").value(799F))
                .andExpect(jsonPath("$.publishedDate").value(LocalDate.now().toString()));
    }

    @Test
    public void testGetBooksByTitle() throws Exception {
        Author author = Author.builder().id(1L).name("Polin").build();
        Category category = Category.builder().id(1L).name("War").build();
        Book book = Book.builder().id(1L).title("The Hammer of Iron").price(799F).publishedDate(LocalDate.now()).author(author).category(category).build();

        when(bookService.getBookByTitle("The Hammer of Iron")).thenReturn(List.of(book));

        mockMvc.perform(get("/book/search/by-title")
                .param("title", "The Hammer of Iron")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("The Hammer of Iron"))
                .andExpect(jsonPath("$[0].authorName").value("Polin"))
                .andExpect(jsonPath("$[0].categoryName").value("War"))
                .andExpect(jsonPath("$[0].price").value(799F))
                .andExpect(jsonPath("$[0].publishedDate").value(LocalDate.now().toString()));
    }

    @Test
    public void testGetBooksByKeyword() throws Exception {
        Author author = Author.builder().id(1L).name("Polin").build();
        Category category = Category.builder().id(1L).name("War").build();
        Book book = Book.builder().id(1L).title("The Hammer of Iron").price(799F).publishedDate(LocalDate.now()).author(author).category(category).build();

        when(bookService.searchBooksByKeyword("hammer")).thenReturn(List.of(book));

        mockMvc.perform(get("/book/search/by-keyword")
                        .param("keyword", "hammer")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("The Hammer of Iron"))
                .andExpect(jsonPath("$[0].authorName").value("Polin"))
                .andExpect(jsonPath("$[0].categoryName").value("War"))
                .andExpect(jsonPath("$[0].price").value(799F))
                .andExpect(jsonPath("$[0].publishedDate").value(LocalDate.now().toString()));
    }

    @Test
    public void testGetAllBooks() throws Exception {
        Author author = Author.builder().id(1L).name("Polin").build();
        Category category = Category.builder().id(1L).name("War").build();
        Book book = Book.builder().id(1L).title("The Hammer of Iron").price(799F).publishedDate(LocalDate.now()).author(author).category(category).build();

        when(bookService.getAllBooks()).thenReturn(List.of(book));

        mockMvc.perform(get("/book")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("The Hammer of Iron"))
                .andExpect(jsonPath("$[0].authorName").value("Polin"))
                .andExpect(jsonPath("$[0].categoryName").value("War"))
                .andExpect(jsonPath("$[0].price").value(799F))
                .andExpect(jsonPath("$[0].publishedDate").value(LocalDate.now().toString()));
    }

    @Test
    public void testGetBooksPriceLessThan() throws Exception {
        Author author = Author.builder().id(1L).name("Polin").build();
        Category category = Category.builder().id(1L).name("War").build();
        Book book = Book.builder().id(1L).title("The Hammer of Iron").price(799F).publishedDate(LocalDate.now()).author(author).category(category).build();

        when(bookService.searchBooksByPriceLessThan(850F)).thenReturn(List.of(book));

        mockMvc.perform(get("/book/search/by-price-less-than")
                        .param("price", "850F")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("The Hammer of Iron"))
                .andExpect(jsonPath("$[0].authorName").value("Polin"))
                .andExpect(jsonPath("$[0].categoryName").value("War"))
                .andExpect(jsonPath("$[0].price").value(799F))
                .andExpect(jsonPath("$[0].publishedDate").value(LocalDate.now().toString()));
    }

    @Test
    public void testGetBooksPriceGreaterThan() throws Exception {
        Author author = Author.builder().id(1L).name("Polin").build();
        Category category = Category.builder().id(1L).name("War").build();
        Book book = Book.builder().id(1L).title("The Hammer of Iron").price(799F).publishedDate(LocalDate.now()).author(author).category(category).build();

        when(bookService.searchBooksByPriceGreaterThan(750F)).thenReturn(List.of(book));

        mockMvc.perform(get("/book/search/by-price-greater-than")
                        .param("price", "750F")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("The Hammer of Iron"))
                .andExpect(jsonPath("$[0].authorName").value("Polin"))
                .andExpect(jsonPath("$[0].categoryName").value("War"))
                .andExpect(jsonPath("$[0].price").value(799F))
                .andExpect(jsonPath("$[0].publishedDate").value(LocalDate.now().toString()));
    }

    @Test
    public void testUpdateBook() throws Exception {
        Author author = Author.builder().id(1L).name("Polin").build();
        Category category = Category.builder().id(1L).name("War").build();
        Book book = Book.builder().id(1L).title("The Hammer of Iron").price(799F).publishedDate(LocalDate.now()).author(author).category(category).build();

        BookUpdateDTO bookUpdateDTO = new BookUpdateDTO();
        bookUpdateDTO.setId(1L);
        bookUpdateDTO.setTitle("The Hammer of Rust");
        bookUpdateDTO.setAuthorName(author);
        bookUpdateDTO.setCategoryName(category);
        bookUpdateDTO.setPrice(999F);
        bookUpdateDTO.setPublishedDate(LocalDate.now());

        Book updatedBook = Book.builder().id(1L).title("The Hammer of Rust").price(999F).publishedDate(LocalDate.now()).author(author).category(category).build();

        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));
        when(bookService.updateBook(eq(1L), any(Book.class))).thenReturn(updatedBook);

        mockMvc.perform(put("/book/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookUpdateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("The Hammer of Rust"))
                .andExpect(jsonPath("$.authorName").value("Polin"))
                .andExpect(jsonPath("$.categoryName").value("War"))
                .andExpect(jsonPath("$.price").value(999F))
                .andExpect(jsonPath("$.publishedDate").value(LocalDate.now().toString()));
    }

    @Test
    public void testDeleteBook() throws Exception {
        doNothing().when(bookService).deleteBookById(1L);

        mockMvc.perform(delete("/book/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Book deleted successfully"));
        verify(bookService, times(1)).deleteBookById(1L);
    }
}














