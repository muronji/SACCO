package com.example.loanandrepayment.transactions;

import com.example.loanandrepayment.customers.Customers;
import com.example.loanandrepayment.loans.Loans;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customers customer;

    @ManyToOne
    @JoinColumn(name = "loan_id", nullable = true)
    private Loans loan;

    private Double amount;

    @Column(nullable = false)
    private String type; // repayment, withdraw, deposit

    private LocalDate transactionDate = LocalDate.now();
}
