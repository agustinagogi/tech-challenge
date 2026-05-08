package com.agustina.library.service;


import com.agustina.library.dto.CreateLoanRequest;
import com.agustina.library.exception.DuplicateActiveLoanException;
import com.agustina.library.exception.InvalidUserNameException;
import com.agustina.library.exception.LoanAlreadyReturnedException;
import com.agustina.library.exception.NoAvailableCopiesException;
import com.agustina.library.model.Book;
import com.agustina.library.model.Loan;
import com.agustina.library.repository.BookRepository;
import com.agustina.library.repository.LoanRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LoanServiceTest {

    private final LoanRepository loanRepository = Mockito.mock(LoanRepository.class);
    private final BookRepository bookRepository = Mockito.mock(BookRepository.class);

    private final LoanService loanService = new LoanService(loanRepository, bookRepository);

    @Test
    void shouldCreateLoan(){
        Book book = new Book(1L, "Prueba", "9780132350884", 3);

        CreateLoanRequest request = new CreateLoanRequest(1L, "Agustina", LocalDate.now().plusDays(14));

        Loan savedLoan = new Loan(1L, "Agustina", request.expectedReturnDate(), null, book);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(loanRepository.save(any(Loan.class))).thenReturn(savedLoan);

        Loan result = loanService.createLoan(request);

        /* System.out.println(result);
        System.out.println(result.getUserName());
        System.out.println(result.getExpectedReturnDate());
        System.out.println(result.getActualReturnDate());
        System.out.println(result.getBook());
        System.out.println(book.getAvailableCopies()); */

        assertEquals(1L, result.getId());
        assertEquals("Agustina", result.getUserName());
        assertEquals(request.expectedReturnDate(), result.getExpectedReturnDate());
        assertEquals(null, result.getActualReturnDate());
        assertEquals(2, book.getAvailableCopies());

        verify(bookRepository).findById(1L);
        verify(loanRepository).save(any(Loan.class));
    }

    @Test
    void shouldThrowWhenNoCopiesAvailable() {

        Book book = new Book(
                1L,
                "Clean Code",
                "9780132350884",
                0
        );

        CreateLoanRequest request = new CreateLoanRequest(
                1L,
                "Agustina",
                LocalDate.now().plusDays(14)
        );

        when(bookRepository.findById(1L))
                .thenReturn(Optional.of(book));

        assertThrows(NoAvailableCopiesException.class, () ->
                loanService.createLoan(request)
        );

        assertEquals(0, book.getAvailableCopies());

        verify(loanRepository, never()).save(any(Loan.class));
    }

    @Test
    void shouldReturnLoan() {

        Book book = new Book(
                1L,
                "Clean Code",
                "9780132350884",
                2
        );

        Loan loan = new Loan(
                1L,
                "Agustina",
                LocalDate.now().plusDays(14),
                null,
                book
        );

        when(loanRepository.findById(1L))
                .thenReturn(Optional.of(loan));

        when(loanRepository.save(any(Loan.class)))
                .thenReturn(loan);

        Loan result = loanService.returnLoan(1L);

        assertEquals(LocalDate.now(), result.getActualReturnDate());

        assertEquals(3, book.getAvailableCopies());

        verify(loanRepository).findById(1L);
        verify(loanRepository).save(any(Loan.class));
    }

    @Test
    void shouldThrowWhenLoanAlreadyReturned(){
        Book book = new Book(1L, "Prueba", "9780132350884", 2);

        Loan loan = new Loan(1L, "Agustina", LocalDate.now().plusDays(14), LocalDate.now(), book);

        when (loanRepository.findById(1L)).thenReturn(Optional.of(loan));

        assertThrows(LoanAlreadyReturnedException.class, () -> loanService.returnLoan(1L));

        assertEquals(2, book.getAvailableCopies());

        verify(loanRepository).findById(1L);

        verify(loanRepository, never()).save(any(Loan.class));
    }

    @Test
    void shouldThrowWhenUserAlreadyHasActiveLoanForBook(){
        Book book = new Book(1L, "Prueba", "9780132350884", 2);

        CreateLoanRequest request = new CreateLoanRequest(1L, "Agustina", LocalDate.now().plusDays(14));

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        when(loanRepository.existsByUserNameAndBookIdAndActualReturnDateIsNull("Agustina", 1L)).thenReturn(true);

        assertThrows(DuplicateActiveLoanException.class, () -> loanService.createLoan(request));

        assertEquals(2, book.getAvailableCopies());

        verify(bookRepository).findById(1L);

        verify(loanRepository, never())
                .save(any(Loan.class));
    }

    @Test
    void shouldThrowWhenUserNameIsEmpty(){
        Book book = new Book(1L, "Prueba", "9780132350884", 2);

        CreateLoanRequest request = new CreateLoanRequest(1L, "Agustina", LocalDate.now().plusDays(14));

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        assertThrows(InvalidUserNameException.class, () -> loanService.createLoan(request));

        assertEquals(2, book.getAvailableCopies());

        verify(bookRepository).findById(1L);

        verify(loanRepository, never())
                .save(any(Loan.class));
    }


}
