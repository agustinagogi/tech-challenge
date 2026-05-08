package com.agustina.library.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CreateBookRequest(@NotBlank String title, @NotBlank String isbn, @Min(0) int availableCopies) {

}
