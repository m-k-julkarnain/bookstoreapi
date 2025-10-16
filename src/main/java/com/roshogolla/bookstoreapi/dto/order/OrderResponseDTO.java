package com.roshogolla.bookstoreapi.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrderResponseDTO {
    private Long id;
    private LocalDate date;
    private Float totalAmount;
  //  private List<String> booklist;
}
