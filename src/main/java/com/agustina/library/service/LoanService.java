package com.agustina.library.service;

import com.agustina.library.dto.CreateLoanRequest;
import com.agustina.library.model.Book;
import com.agustina.library.model.Loan;
import com.agustina.library.repository.BookRepository;
import com.agustina.library.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class LoanService {

    public LoanService(LoanRepository loanRepository, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
    }
    private final LoanRepository loanRepository;

    private final BookRepository bookRepository;


    // Method to create a loan
    public Loan createLoan(CreateLoanRequest request){
        Book book = bookRepository.findById(request.bookId()).orElseThrow();

        book.setAvailableCopies(book.getAvailableCopies() - 1);

        Loan loan = new Loan (null, request.userName(), request.expectedReturnDate(), null, book); // actualReturnDate is null until the loan is returned

        return loanRepository.save(loan);
    }

    // Method to return a loan
    public Loan returnLoan(Long loanId){
        Loan loan = loanRepository.findById(loanId).orElseThrow();

        loan.setActualReturnDate(LocalDate.now());

        Book book = loan.getBook();

        book.setAvailableCopies(book.getAvailableCopies() + 1);

        return loanRepository.save(loan);
    }
}
