package com.agustina.library.controller;

import com.agustina.library.dto.CreateLoanRequest;
import com.agustina.library.model.Loan;
import com.agustina.library.service.LoanService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    public Loan createLoan(@RequestBody CreateLoanRequest request){
        return loanService.createLoan(request);
    }
}
