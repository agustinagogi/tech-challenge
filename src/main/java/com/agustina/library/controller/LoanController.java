package com.agustina.library.controller;

import com.agustina.library.dto.CreateLoanRequest;
import com.agustina.library.model.Loan;
import com.agustina.library.service.LoanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    public Loan createLoan(@RequestBody CreateLoanRequest request){
        return loanService.createLoan(request);
    }

    @PutMapping("/{id}/return")
    public Loan returnLoan(@PathVariable Long id){
        return loanService.returnLoan(id);
    }

    @GetMapping("/active")
    public List<Loan> getActiveLoans(@RequestParam String userName){
        return loanService.getActiveLoans(userName);
    }
}
