package com.roshogolla.bookstoreapi.service;

import com.roshogolla.bookstoreapi.entity.Author;
import com.roshogolla.bookstoreapi.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    //Create
    public Author addAuthor(Author author) {
        return authorRepository.save(author);
    }

    //Read by ID
    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    //Search by name
    public List<Author> searchAuthorsByName(String name) {
        return authorRepository.findByName(name);
    }

    //Search by name keyword
    public List<Author> searchAuthorsByKeyword(String keyword) {
        return authorRepository.findByNameContainingIgnoreCase(keyword);
    }

    //Read all
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    //Update
    public Author updateAuthor(Long id, Author updatedAuthor) {
        return authorRepository.findById(id)
                .map(author -> {
                    author.setName(updatedAuthor.getName());
                    author.setBio(updatedAuthor.getBio());
                    return authorRepository.save(author);
                })
                .orElseThrow(() -> new RuntimeException("Author not found with id " + id));
    }

    //Delete
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}
