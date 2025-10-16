package com.roshogolla.bookstoreapi.dto.book;

import com.roshogolla.bookstoreapi.entity.Author;
import com.roshogolla.bookstoreapi.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class BookResponseDTO {

    private Long id;
    private String title;
    private Float price;
    private String authorName;
    private String categoryName;
    private LocalDate publishedDate;
}
