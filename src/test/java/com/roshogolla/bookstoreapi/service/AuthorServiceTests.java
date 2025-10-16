package com.roshogolla.bookstoreapi.service;

import com.roshogolla.bookstoreapi.entity.Author;
import com.roshogolla.bookstoreapi.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTests {
    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    @Test
    public void testAddAuthor() {
        Author author = Author.builder().name("Nilop").bio("I love animals").build();

        when(authorRepository.save(author)).thenReturn(author);

        Author savedAuthor = authorService.addAuthor(author);

        assertThat(savedAuthor).isNotNull();
        assertThat(savedAuthor.getName()).isEqualTo("Nilop");
        assertThat(savedAuthor.getBio()).isEqualTo("I love animals");
        verify(authorRepository, times(1)).save(author);
    }

    @Test
    public void testFindAuthorByID() {
        Author author = Author.builder().id(1L).name("Nilop").bio("I love animals").build();

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        Optional<Author> foundAuthor = authorService.getAuthorById(1L);

        assertThat(foundAuthor).isNotEmpty();
        assertThat(foundAuthor.get().getName()).isEqualTo("Nilop");
        assertThat(foundAuthor.get().getBio()).isEqualTo("I love animals");
        verify(authorRepository, times(1)).findById(1L);
    }

    @Test
    public void testFindAuthorsByName() {
        Author author = Author.builder().id(1L).name("Nilop").bio("I love animals").build();

        when(authorRepository.findByName("Nilop")).thenReturn(List.of(author));

        List<Author> foundAuthor = authorService.searchAuthorsByName("Nilop");

        assertThat(foundAuthor).isNotEmpty();
        assertThat(foundAuthor.get(0).getName()).isEqualTo("Nilop");
        assertThat(foundAuthor.get(0).getBio()).isEqualTo("I love animals");
        verify(authorRepository, times(1)).findByName("Nilop");
    }

    @Test
    public void testFindAuthorsByKeyword() {
        Author author = Author.builder().id(1L).name("Nilop").bio("I love animals").build();

        when(authorRepository.findByNameContainingIgnoreCase("nil")).thenReturn(List.of(author));

        List<Author> foundAuthor = authorService.searchAuthorsByKeyword("nil");

        assertThat(foundAuthor).isNotEmpty();
        assertThat(foundAuthor.get(0).getName()).isEqualTo("Nilop");
        assertThat(foundAuthor.get(0).getBio()).isEqualTo("I love animals");
        verify(authorRepository, times(1)).findByNameContainingIgnoreCase("nil");
    }

    @Test
    public void testFindAllAuthors() {
        Author author = Author.builder().id(1L).name("Nilop").bio("I love animals").build();

        when(authorRepository.findAll()).thenReturn(List.of(author));

        List<Author> foundAuthor = authorService.getAllAuthors();

        assertThat(foundAuthor).isNotEmpty();
        assertThat(foundAuthor.get(0).getName()).isEqualTo("Nilop");
        assertThat(foundAuthor.get(0).getBio()).isEqualTo("I love animals");
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    public void testUpdateAuthor() {
        Author author = Author.builder().id(1L).name("Nilop").bio("I love animals").build();
        Author updatedAuthor = Author.builder().id(1L).name("Rupa").bio("I love animals and gardening").build();

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(authorRepository.save(author)).thenReturn(updatedAuthor);

        Author foundAuthor = authorService.updateAuthor(1L, updatedAuthor);

        assertThat(foundAuthor).isNotNull();
        assertThat(foundAuthor.getName()).isEqualTo("Rupa");
        assertThat(foundAuthor.getBio()).isEqualTo("I love animals and gardening");
        verify(authorRepository, times(1)).findById(1L);
        verify(authorRepository, times(1)).save(author);
    }

    @Test
    public void testDeleteAuthor() {
        Author author = Author.builder().id(1L).name("Nilop").bio("I love animals").build();

        authorService.deleteAuthor(1L);

        verify(authorRepository, times(1)).deleteById(1L);

    }
}






















