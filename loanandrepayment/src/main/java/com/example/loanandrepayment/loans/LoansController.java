package com.example.loanandrepayment.loans;

import com.example.loanandrepayment.DTOs.LoansDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/loans")
public class LoansController {
    private final LoanService loanService;
    public LoansController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createLoan(@RequestBody LoansDTO loansDTO) {
        try {
            Loans createdLoan = loanService.createLoan(loansDTO);
            return new ResponseEntity<>(createdLoan, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create loan: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLoanById(@PathVariable Long id) {
        try {
            Optional<Loans> loanOptional = loanService.getLoanById(id);
            if (loanOptional.isPresent()) {
                return new ResponseEntity<>(loanOptional.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Loan not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to get loan: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllLoans() {
        try {
            List<Loans> loans = loanService.getAllLoans();
            return new ResponseEntity<>(loans, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to get loans: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateLoan(@PathVariable Long id, @RequestBody LoansDTO loansDTO) {
        try {
            Loans updatedLoan = loanService.updateLoan(id, loansDTO);
            return new ResponseEntity<>(updatedLoan, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update loan: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteLoan(@RequestParam Long id) {
        try {
            loanService.deleteLoan(id);
            return new ResponseEntity<>("Loan deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete loan: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
