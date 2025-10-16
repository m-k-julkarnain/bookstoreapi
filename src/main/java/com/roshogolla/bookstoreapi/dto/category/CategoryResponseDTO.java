package com.roshogolla.bookstoreapi.dto.category;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryResponseDTO {
    private Long id;
    private String name;
    private List<String> bookTitles;

    public CategoryResponseDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
