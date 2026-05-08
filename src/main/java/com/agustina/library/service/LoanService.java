package com.agustina.library.service;

import com.agustina.library.dto.CreateLoanRequest;
import com.agustina.library.exception.*;
import com.agustina.library.model.Book;
import com.agustina.library.model.Loan;
import com.agustina.library.repository.BookRepository;
import com.agustina.library.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {

    private final LoanRepository loanRepository;

    private final BookRepository bookRepository;

    public LoanService(LoanRepository loanRepository, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
    }

    // Method to create a loan
    public Loan createLoan(CreateLoanRequest request){
        Book book = bookRepository.findById(request.bookId()).orElseThrow(() -> new BookNotFoundException("Book not found."));

        if(request.userName() == null || request.userName().isBlank()){
            throw new InvalidUserNameException("User name cannot be empty.");
        }

        boolean userAlreadyHasBook = loanRepository.existsByUserNameAndBookIdAndActualReturnDateIsNull(request.userName(), request.bookId());

        if (userAlreadyHasBook){
            throw new DuplicateActiveLoanException("User already has an active loan for this book.");
        }

        if (request.expectedReturnDate().isBefore(LocalDate.now())){
            throw new InvalidReturnDateException("Expected return date cannot be in the past.");
        }

        // If there's no available copies, we get an error
        if (book.getAvailableCopies() <= 0){
            throw new NoAvailableCopiesException("No copies available for this book.");
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);

        Loan loan = new Loan (null, request.userName(), request.expectedReturnDate(), null, book); // actualReturnDate is null until the loan is returned

        return loanRepository.save(loan);
    }

    // Method to return a loan
    public Loan returnLoan(Long loanId){
        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new LoanNotFoundException("Loan not found."));

        if (loan.getActualReturnDate() != null){
            throw new LoanAlreadyReturnedException("Loan has already been returned");
        }

        loan.setActualReturnDate(LocalDate.now());

        Book book = loan.getBook();

        book.setAvailableCopies(book.getAvailableCopies() + 1);

        return loanRepository.save(loan);
    }

    public List<Loan> getActiveLoans(String userName){
        return loanRepository.findByUserNameAndActualReturnDateIsNull(userName);
    }
}
