package com.example.loanandrepayment.transactions;

import com.example.loanandrepayment.DTOs.TransactionsDTO;
import com.example.loanandrepayment.customers.Customers;
import com.example.loanandrepayment.customers.CustomersRepository;
import com.example.loanandrepayment.loans.Loans;
import com.example.loanandrepayment.loans.LoansRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionsService {
    private final TransactionsRepository transactionsRepository;
    private final CustomersRepository customersRepository;
    private final LoansRepository loansRepository;

    public TransactionsService(TransactionsRepository transactionsRepository, CustomersRepository customersRepository, LoansRepository loansRepository) {
        this.transactionsRepository = transactionsRepository;
        this.customersRepository = customersRepository;
        this.loansRepository = loansRepository;
    }

    public Transactions createDeposit(TransactionsDTO transactionsDTO) {
        Customers customer = customersRepository.findById(transactionsDTO.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        customer.setBalance(customer.getBalance() + transactionsDTO.getAmount());

        Transactions transaction = new Transactions();
        transaction.setCustomer(customer);
        transaction.setAmount(transactionsDTO.getAmount());
        transaction.setType("deposit");

        transactionsRepository.save(transaction);
        customersRepository.save(customer);
        return transaction;
    }

    public Transactions createWithdraw(TransactionsDTO transactionsDTO) {
        Customers customer = customersRepository.findById(transactionsDTO.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        validateWithdrawalAmount(customer, transactionsDTO.getAmount());
        customer.setBalance(customer.getBalance() - transactionsDTO.getAmount());

        Transactions transaction = new Transactions();
        transaction.setCustomer(customer);
        transaction.setAmount(transactionsDTO.getAmount());
        transaction.setType("withdraw");

        transactionsRepository.save(transaction);
        customersRepository.save(customer);
        return transaction;
    }

    public Transactions createRepayment(TransactionsDTO transactionsDTO) {
        if (transactionsDTO.getLoanId() == null) {
            throw new IllegalArgumentException("Loan ID must not be null for repayments");
        }
        Loans loan = loansRepository.findById(transactionsDTO.getLoanId())
                .orElseThrow(() -> new EntityNotFoundException("Loan not found"));
        Customers customer = customersRepository.findById(loan.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found for the given loan"));

        validateRepaymentAmount(loan, transactionsDTO.getAmount());
        loan.setBalance(loan.getBalance() - transactionsDTO.getAmount());

        Transactions transaction = new Transactions();
        transaction.setLoan(loan);
        transaction.setCustomer(customer);
        transaction.setAmount(transactionsDTO.getAmount());
        transaction.setType("repayment");

        transactionsRepository.save(transaction);
        loansRepository.save(loan);
        return transaction;
    }

    public Long getCustomerIdByLoanId(Long loanId) {
        return loansRepository.findById(loanId)
                .map(Loans::getCustomerId)
                .orElseThrow(() -> new EntityNotFoundException("Loan not found for the given ID: " + loanId));
    }

    public List<Transactions> getAllTransactions() {
        return transactionsRepository.findAll();
    }

    public Optional<Transactions> getTransactionsByCustomerId(Long CustomerId) {
        if (transactionsRepository.existsById(CustomerId)){
            return transactionsRepository.findById(CustomerId);
        } else {
            throw new EntityNotFoundException("Customer's transactions not found");
        }
    }

    public void deleteTransaction(Long TransactionId) {
        if (transactionsRepository.existsById(TransactionId)) {
            transactionsRepository.deleteById(TransactionId);
        } else {
            throw new EntityNotFoundException("Transaction ID not found");
        }
    }

    public Transactions updateTransaction(Long id, TransactionsDTO transactionsDTO) {
        Optional<Transactions> transactionOptional = transactionsRepository.findById(id);
        if (transactionOptional.isPresent()) {
            Transactions existingTransaction = transactionOptional.get();
            existingTransaction.setAmount(transactionsDTO.getAmount());
            existingTransaction.setType(transactionsDTO.getType());
            return transactionsRepository.save(existingTransaction);
        } else {
            throw new EntityNotFoundException("Transaction not found");
        }
    }

    private void validateWithdrawalAmount(Customers customer, Double amount) {
        if (customer.getBalance() < amount) {
            throw new InsufficientFundsException("Insufficient balance");
        }
    }

    private void validateRepaymentAmount(Loans loan, Double amount) {
        if (loan.getBalance() < amount) {
            throw new InsufficientFundsException("Insufficient loan balance");
        }
    }

    public static class EntityNotFoundException extends RuntimeException {
        public EntityNotFoundException(String message) {
            super(message);
        }
    }

    public static class InsufficientFundsException extends RuntimeException {
        public InsufficientFundsException(String message) {
            super(message);
        }
    }
}