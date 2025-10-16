package com.roshogolla.bookstoreapi.repository;

import com.roshogolla.bookstoreapi.entity.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatTemporal;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class AuthorRepositoryTests {
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void testFindById() {
        Author author = Author.builder().name("Polin").bio("I love animals").build();

        Author savedAuthor = authorRepository.save(author);

        Optional<Author> foundAuthor = authorRepository.findById(savedAuthor.getId());

        assertThat(foundAuthor).isNotEmpty();
        assertThat(foundAuthor.get().getName()).isEqualTo("Polin");
        assertThat(foundAuthor.get().getBio()).isEqualTo("I love animals");
    }

    @Test
    public void testFindByName() {
        Author author = Author.builder().name("Polin").bio("I love animals").build();

        authorRepository.save(author);

        List<Author> foundAuthors = authorRepository.findByName("Polin");

        assertThat(foundAuthors).isNotEmpty();
        assertThat(foundAuthors.get(0).getName()).isEqualTo("Polin");
        assertThat(foundAuthors.get(0).getBio()).isEqualTo("I love animals");
    }

    @Test
    public void testFindByKeywordContainingIgnoreCase() {
        Author author = Author.builder().name("Polin").bio("I love animals").build();
        authorRepository.save(author);

        List<Author> foundAuthors = authorRepository.findByNameContainingIgnoreCase("polin");

        assertThat(foundAuthors.get(0).getName()).isEqualTo("Polin");
        assertThat(foundAuthors.get(0).getBio()).isEqualTo("I love animals");
    }

    @Test
    public void testUpdateAuthor() {
        Author author = Author.builder().name("Polin").bio("I love animals").build();
        Author savedAuthor = authorRepository.save(author);

        savedAuthor.setName("Rupa");
        savedAuthor.setBio("I love animals and gardening");

        Author updatedAuthor = authorRepository.save(savedAuthor);

        Optional<Author> foundAuthor = authorRepository.findById(updatedAuthor.getId());

        assertThat(foundAuthor).isNotEmpty();
        assertThat(foundAuthor.get().getName()).isEqualTo("Rupa");
        assertThat(foundAuthor.get().getBio()).isEqualTo("I love animals and gardening");
    }

    @Test
    public void testDeleteAuthor() {
        Author author = Author.builder().name("Polin").bio("I love animals").build();
        Author savedAuthor = authorRepository.save(author);

        authorRepository.deleteById(savedAuthor.getId());

        Optional<Author> deletedAuthor = authorRepository.findById(savedAuthor.getId());

        assertThat(deletedAuthor).isEmpty();

    }
}

