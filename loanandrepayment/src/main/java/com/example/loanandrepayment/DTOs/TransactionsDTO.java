package com.example.loanandrepayment.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionsDTO {
    private Long customerId;
    private Double amount;
    private String type; // "deposit", "withdraw", "repayment"
    private Long loanId; // This is now optional, primarily used for "repayment"

    public TransactionsDTO(Long customerId, Double amount, String type) {
        this.customerId = customerId;
        this.amount = amount;
        this.type = type;
        this.loanId = null; // Default to null when loanId is not applicable
    }


}