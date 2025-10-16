package com.roshogolla.bookstoreapi.dto.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class OrderCreateDTO {
    @NotNull(message = "Date can't be empty")
    private LocalDate date;

    @NotNull(message = "At least have one book")
    private List<Long> bookIDs;

    @NotNull(message = "Price can't be null")
    @Positive(message = "Price has to be a positive number")
    private Float totalAmount;
}
