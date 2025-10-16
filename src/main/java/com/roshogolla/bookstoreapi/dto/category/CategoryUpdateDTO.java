package com.roshogolla.bookstoreapi.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryUpdateDTO {
    @NotNull(message = "Category id can't be null")
    private Long id;

    @NotBlank(message = "Category name can't be blank")
    private String name;
}
