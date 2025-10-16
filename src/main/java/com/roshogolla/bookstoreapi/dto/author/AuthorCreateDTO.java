package com.roshogolla.bookstoreapi.dto.author;

import com.roshogolla.bookstoreapi.entity.Book;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AuthorCreateDTO {
    @NotBlank(message = "Author name can't be empty")
    private String name;

    private String bio;


}
