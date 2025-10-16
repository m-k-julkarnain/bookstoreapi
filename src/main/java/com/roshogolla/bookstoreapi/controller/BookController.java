package com.roshogolla.bookstoreapi.controller;

import com.roshogolla.bookstoreapi.dto.book.BookCreateDTO;
import com.roshogolla.bookstoreapi.dto.book.BookResponseDTO;
import com.roshogolla.bookstoreapi.dto.book.BookUpdateDTO;
import com.roshogolla.bookstoreapi.entity.Book;
import com.roshogolla.bookstoreapi.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    //Create a new book
    @PostMapping
    public ResponseEntity<BookResponseDTO> createBook(@Valid @RequestBody BookCreateDTO bookCreateDTO) {
        Book book = new Book();
        book.setTitle(bookCreateDTO.getTitle());
        book.setPrice(bookCreateDTO.getPrice());
        book.setPublishedDate(bookCreateDTO.getPublishedDate());
        book.setAuthor(bookCreateDTO.getAuthorName());
        book.setCategory(bookCreateDTO.getCategoryName());

        Book savedBook = bookService.addBook(book);

        BookResponseDTO bookResponseDTO = new BookResponseDTO(
                savedBook.getId(),
                savedBook.getTitle(),
                savedBook.getPrice(),
                savedBook.getAuthor().getName(),
                savedBook.getCategory().getName(),
                savedBook.getPublishedDate()
        );

        return ResponseEntity.ok(bookResponseDTO);
    }

    //Get book by id
    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id " + id));

        BookResponseDTO bookResponseDTO = new BookResponseDTO(
                book.getId(),
                book.getTitle(),
                book.getPrice(),
                book.getAuthor().getName(),
                book.getCategory().getName(),
                book.getPublishedDate()
        );

        return ResponseEntity.ok(bookResponseDTO);
    }

    //Search books by title
    @GetMapping("/search/by-title")
    public ResponseEntity<List<BookResponseDTO>> searchBooksByTitle(@RequestParam String title) {
        List<BookResponseDTO> books = bookService.getBookByTitle(title)
                .stream()
                .map(book -> new BookResponseDTO(
                        book.getId(),
                        book.getTitle(),
                        book.getPrice(),
                        book.getAuthor().getName(),
                        book.getCategory().getName(),
                        book.getPublishedDate()
                    )).collect(Collectors.toList());
        return ResponseEntity.ok(books);
    }

    //Search books by keyword
    @GetMapping("/search/by-keyword")
    public ResponseEntity<List<BookResponseDTO>> getBooksByKeyword(@RequestParam String keyword) {
        List<BookResponseDTO> books = bookService.searchBooksByKeyword(keyword)
                .stream()
                .map(book -> new BookResponseDTO(
                        book.getId(),
                        book.getTitle(),
                        book.getPrice(),
                        book.getAuthor().getName(),
                        book.getCategory().getName(),
                        book.getPublishedDate()
                )).collect(Collectors.toList());
        return ResponseEntity.ok(books);
    }

    //Get all books
    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAllBooks(){
        List<BookResponseDTO> books = bookService.getAllBooks()
                .stream()
                .map(book -> new BookResponseDTO(
                        book.getId(),
                        book.getTitle(),
                        book.getPrice(),
                        book.getAuthor().getName(),
                        book.getCategory().getName(),
                        book.getPublishedDate()
                )).collect(Collectors.toList());
        return ResponseEntity.ok(books);
    }

    //Search books price less than the target
    @GetMapping("/search/by-price-less-than")
    public ResponseEntity<List<BookResponseDTO>> getBooksByPriceLessThan(@RequestParam Float price) {
        List<BookResponseDTO> books = bookService.searchBooksByPriceLessThan(price)
                .stream()
                .map(book -> new BookResponseDTO(
                        book.getId(),
                        book.getTitle(),
                        book.getPrice(),
                        book.getAuthor().getName(),
                        book.getCategory().getName(),
                        book.getPublishedDate()
                )).collect(Collectors.toList());
        return ResponseEntity.ok(books);
    }

    //Search books price greater than the target
    @GetMapping("/search/by-price-greater-than")
    public ResponseEntity<List<BookResponseDTO>> getBooksPriceGreaterThan(@RequestParam Float price) {
        List<BookResponseDTO> books = bookService.searchBooksByPriceGreaterThan(price)
                .stream()
                .map(book -> new BookResponseDTO(
                        book.getId(),
                        book.getTitle(),
                        book.getPrice(),
                        book.getAuthor().getName(),
                        book.getCategory().getName(),
                        book.getPublishedDate()
                )).collect(Collectors.toList());
        return ResponseEntity.ok(books);
    }

    //Update books
    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable Long id, @Valid @RequestBody BookUpdateDTO bookUpdateDTO) {
        Book updatedBook  = new Book();
        updatedBook.setId(id); //Directly taken from the path
        updatedBook.setTitle(bookUpdateDTO.getTitle());
        updatedBook.setPrice(bookUpdateDTO.getPrice());
        updatedBook.setPublishedDate(bookUpdateDTO.getPublishedDate());

        Book savedBook = bookService.updateBook(id, updatedBook);

        BookResponseDTO bookResponseDTO = new BookResponseDTO(
                savedBook.getId(),
                savedBook.getTitle(),
                savedBook.getPrice(),
                savedBook.getAuthor().getName(),
                savedBook.getCategory().getName(),
                savedBook.getPublishedDate()
        );

        return ResponseEntity.ok(bookResponseDTO);
    }

    //Delete book by id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return ResponseEntity.ok("Book deleted successfully");
    }


}


















