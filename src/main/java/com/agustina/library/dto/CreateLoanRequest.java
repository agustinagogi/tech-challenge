package com.agustina.library.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateLoanRequest (@NotNull(message = "Book id is required") Long bookId, @NotBlank(message = "User name cannot be empty") String userName, @NotNull(message = "Expected return date is required") @FutureOrPresent(message = "Expected return date cannot be in the past") LocalDate expectedReturnDate) {
}
