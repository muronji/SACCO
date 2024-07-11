package com.example.loanandrepayment.loans;

import com.example.loanandrepayment.transactions.Transactions;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Loans {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = false)
    private Double loanAmount;

    @Column(nullable = false)
    private Integer duration;

    @Column(nullable = false)
    private String repaymentMethod;

    @Column(nullable = false)
    private Double balance;

    @OneToMany(mappedBy = "loan")
    private List<Transactions> transactions;


}
