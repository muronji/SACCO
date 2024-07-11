package com.example.loanandrepayment.DTOs;

public record CustomersDTO(
        Long customerId,
        String name,
        String email,
        Double balance
) {
}
