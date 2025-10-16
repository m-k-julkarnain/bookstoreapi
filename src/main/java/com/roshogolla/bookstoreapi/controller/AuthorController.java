package com.roshogolla.bookstoreapi.controller;

import com.roshogolla.bookstoreapi.dto.author.AuthorCreateDTO;
import com.roshogolla.bookstoreapi.dto.author.AuthorResponseDTO;
import com.roshogolla.bookstoreapi.dto.author.AuthorUpdateDTO;
import com.roshogolla.bookstoreapi.entity.Author;
import com.roshogolla.bookstoreapi.entity.Book;
import com.roshogolla.bookstoreapi.exceptions.ResourceNotFoundException;
import com.roshogolla.bookstoreapi.service.AuthorService;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import org.hibernate.usertype.internal.AbstractTimeZoneStorageCompositeUserType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    //Create a new author
    @PostMapping
    public ResponseEntity<AuthorResponseDTO> createAuthor(@Valid @RequestBody AuthorCreateDTO authorCreateDTO) {
        Author author = new Author();
        author.setName(authorCreateDTO.getName());
        author.setBio(authorCreateDTO.getBio());

        Author savedAuthor = authorService.addAuthor(author);

        List<String> bookTitles = savedAuthor.getBooks() != null
                ? savedAuthor.getBooks().stream().map(Book::getTitle).toList()
                : List.of();

        AuthorResponseDTO authorResponseDTO = new AuthorResponseDTO(
                savedAuthor.getId(),
                savedAuthor.getName(),
                savedAuthor.getBio(),
                bookTitles
        );

        return ResponseEntity.ok(authorResponseDTO);
    }

    //Get author by id
    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseDTO> getAuthorById(@PathVariable Long id) {
        Author author = authorService.getAuthorById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id " + id));

        List<String> bookTitles = author.getBooks() != null
                ? author.getBooks().stream().map(Book::getTitle).toList()
                : List.of();

        AuthorResponseDTO authorResponseDTO = new AuthorResponseDTO(
                author.getId(),
                author.getName(),
                author.getBio(),
                bookTitles
        );

        return ResponseEntity.ok(authorResponseDTO);
    }

    //Search author by name
    @GetMapping("/search/by-name")
    public ResponseEntity<List<AuthorResponseDTO>> searchAuthorByName(@RequestParam String name) {
        List<AuthorResponseDTO> authors = authorService.searchAuthorsByName(name)
                .stream()
                .map(author ->  new AuthorResponseDTO(
                        author.getId(),
                        author.getName(),
                        author.getBio(),
                        author.getBooks() != null
                                ? author.getBooks().stream().map(Book::getTitle).toList()
                                : List.of()
                )).toList();
        return ResponseEntity.ok(authors);
    }

    //Search author by name keyword
    @GetMapping("/search/by-keyword")
    public ResponseEntity<List<AuthorResponseDTO>> searchAuthorByKeyword(@RequestParam String keyword) {
        List<AuthorResponseDTO> authors = authorService.searchAuthorsByKeyword(keyword)
                .stream()
                .map(author ->  new AuthorResponseDTO(
                        author.getId(),
                        author.getName(),
                        author.getBio(),
                        author.getBooks() != null
                                ? author.getBooks().stream().map(Book::getTitle).toList()
                                : List.of()
                )).toList();
        return ResponseEntity.ok(authors);
    }

    //Get all authors
    @GetMapping
    public ResponseEntity<List<AuthorResponseDTO>> getAllAuthors() {
        List<AuthorResponseDTO> authors = authorService.getAllAuthors()
                .stream()
                .map(author ->  new AuthorResponseDTO(
                        author.getId(),
                        author.getName(),
                        author.getBio(),
                        author.getBooks() != null
                                ? author.getBooks().stream().map(Book::getTitle).toList()
                                : List.of()
                )).toList();
        return ResponseEntity.ok(authors);
    }

    //Update authors
    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponseDTO> updateAuthor(@PathVariable Long id ,@Valid @RequestBody AuthorUpdateDTO authorUpdateDTO) {
        Author author = authorService.getAuthorById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id " + id));

        author.setName(authorUpdateDTO.getName());
        author.setBio(authorUpdateDTO.getBio());

        Author savedAuthor = authorService.updateAuthor(id, author);

        List<String> bookTitles = savedAuthor.getBooks() != null
                ? savedAuthor.getBooks().stream().map(Book::getTitle).toList()
                : List.of();

        AuthorResponseDTO authorResponseDTO = new AuthorResponseDTO(
                savedAuthor.getId(),
                savedAuthor.getName(),
                savedAuthor.getBio(),
                bookTitles
        );

        return ResponseEntity.ok(authorResponseDTO);
    }

    //Delete author
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.ok("Author deleted successfully");
    }
}






















