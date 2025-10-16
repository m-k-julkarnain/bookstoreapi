package com.roshogolla.bookstoreapi.dto.book;

import com.roshogolla.bookstoreapi.entity.Author;
import com.roshogolla.bookstoreapi.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookUpdateDTO {
    @NotNull(message = "Book id can't be null")
    private Long id;

    @NotBlank(message = "Book title can't be blank")
    private String title;

    @NotNull(message = "Book price can't be null")
    @Positive(message = "Price has to be a positive number")
    private Float price;

    @NotNull(message = "Date can't be null")
    private LocalDate publishedDate;

    @NotNull(message = "Author name can't be blank")
    private Author authorName;

    @NotNull(message = "Category can't be blank")
    private Category categoryName;
}
