package com.example.loanandrepayment.loans;

import com.example.loanandrepayment.DTOs.LoansDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanService {
    private final LoansRepository loansRepository;

    public LoanService(LoansRepository loansRepository) {
        this.loansRepository = loansRepository;
    }

    public Loans createLoan(LoansDTO loansDTO) {
        Loans loan = new Loans();
        loan.setCustomerId(loansDTO.customerId());
        loan.setLoanAmount(loansDTO.loanAmount());
        loan.setDuration(loansDTO.duration());
        loan.setRepaymentMethod(loansDTO.repaymentMethod());
        loan.setBalance(loansDTO.loanAmount());

        return loansRepository.save(loan);
    }

    public Optional<Loans> getLoanById(Long id) {
        return loansRepository.findById(id);
    }

    public List<Loans> getAllLoans() {
        return loansRepository.findAll();
    }

    public Loans updateLoan(Long id, LoansDTO loansDTO) {
        Optional<Loans> loanOptional = loansRepository.findById(id);
        if (loanOptional.isPresent()) {
            Loans existingLoan = loanOptional.get();
            existingLoan.setCustomerId(loansDTO.customerId());
            existingLoan.setLoanAmount(loansDTO.loanAmount());
            existingLoan.setRepaymentMethod(loansDTO.repaymentMethod());
            existingLoan.setDuration(loansDTO.duration());
            existingLoan.setBalance(loansDTO.loanAmount());

            return loansRepository.save(existingLoan);
        } else {
            throw new RuntimeException("Loan not found");
        }
    }

    public void deleteLoan(Long id) {
        if (loansRepository.existsById(id)) {
            loansRepository.deleteById(id);
        } else {
            throw new RuntimeException("Loan ID not found");
        }
    }
}
