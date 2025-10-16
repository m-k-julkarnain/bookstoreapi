package com.roshogolla.bookstoreapi.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryCreateDTO {

    @NotBlank(message = "Category name can't be blank")
    private String name;
}
