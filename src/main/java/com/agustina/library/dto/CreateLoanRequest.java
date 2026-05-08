package com.agustina.library.dto;

import java.time.LocalDate;

public record CreateLoanRequest (Long bookId, String userName, LocalDate expectedReturnDate) {
}
