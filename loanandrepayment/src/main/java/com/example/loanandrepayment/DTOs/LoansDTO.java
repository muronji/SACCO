package com.example.loanandrepayment.DTOs;

public record LoansDTO(
        Long customerId,
        Double loanAmount,
        Integer duration,
        String repaymentMethod
) {
}
