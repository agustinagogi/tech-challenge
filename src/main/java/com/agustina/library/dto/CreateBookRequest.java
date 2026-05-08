package com.agustina.library.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CreateBookRequest(@NotBlank(message = "Title cannot be empty") String title, @NotBlank(message = "ISBN cannot be empty") String isbn, @Min(value = 0, message = "Available copies cannot be negative") int availableCopies) {

}
