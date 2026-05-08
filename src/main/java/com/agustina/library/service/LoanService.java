package com.agustina.library.service;

import com.agustina.library.dto.CreateLoanRequest;
import com.agustina.library.model.Book;
import com.agustina.library.model.Loan;
import com.agustina.library.repository.BookRepository;
import com.agustina.library.repository.LoanRepository;
import org.springframework.stereotype.Service;

@Service
public class LoanService {

    public LoanService(LoanRepository loanRepository, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
    }
    private final LoanRepository loanRepository;

    private final BookRepository bookRepository;

    public Loan createLoan(CreateLoanRequest request){
        Book book = bookRepository.findById(request.bookId()).orElseThrow();

        book.setAvailableCopies(book.getAvailableCopies() - 1);

        Loan loan = new Loan (null, request.userName(), request.expectedReturnDate(), null, book); // ReturnDate null porque aun no ha devuelto el prestamo

        return loanRepository.save(loan);
    }
}
