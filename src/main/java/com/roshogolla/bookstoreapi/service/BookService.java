package com.roshogolla.bookstoreapi.service;

import com.roshogolla.bookstoreapi.entity.Book;
import com.roshogolla.bookstoreapi.exceptions.ResourceNotFoundException;
import com.roshogolla.bookstoreapi.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    //Create
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    //Read by ID
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    //Read all books
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    //Read by title
    public List<Book> getBookByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    //Search by keyword in the title
    public List<Book> searchBooksByKeyword(String keyword) {
        return bookRepository.findByTitleContainingIgnoreCase(keyword);
    }

    //Search books by price less than
    public List<Book> searchBooksByPriceLessThan(Float price) {
        return bookRepository.findByPriceLessThan(price);
    }

    //Search books by price greater than
    public List<Book> searchBooksByPriceGreaterThan(Float price) {
        return bookRepository.findByPriceGreaterThan(price);
    }

    //Update book
    public Book updateBook(Long id, Book updatedBook) {
        return bookRepository.findById(id)
                .map(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setAuthor(updatedBook.getAuthor());
                    book.setCategory(updatedBook.getCategory());
                    book.setPrice(updatedBook.getPrice());
                    book.setPublishedDate(updatedBook.getPublishedDate());
                    return bookRepository.save(updatedBook);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id));
    }

    //Delete book by id
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }
}
