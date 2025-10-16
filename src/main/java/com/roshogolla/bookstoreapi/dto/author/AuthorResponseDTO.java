package com.roshogolla.bookstoreapi.dto.author;

import com.roshogolla.bookstoreapi.entity.Book;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AuthorResponseDTO {
    private Long id;
    private String name;
    private String bio;
    private List<String> bookTitles;
}
