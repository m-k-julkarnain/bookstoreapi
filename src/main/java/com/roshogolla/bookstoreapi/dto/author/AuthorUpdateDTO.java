package com.roshogolla.bookstoreapi.dto.author;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorUpdateDTO {
    @NotNull(message = "Author id can't be null")
    private Long id;

    @NotBlank(message = "Author name can't be empty")
    private String name;

    private String bio;

}
