package com.example.loanandrepayment.transactions;

import com.example.loanandrepayment.DTOs.TransactionsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionsController {
 private final TransactionsService transactionsService;

 @Autowired
    public TransactionsController(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestParam Long customerId, @RequestParam Double amount) {
        try {
            TransactionsDTO transactionsDTO = new TransactionsDTO(customerId, amount, "deposit", null);
            transactionsService.createDeposit(transactionsDTO);
            return new ResponseEntity<>("Deposit successful", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to deposit: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestParam Long customerId, @RequestParam Double amount) {
        try {
            TransactionsDTO transactionsDTO = new TransactionsDTO(customerId, amount, "withdraw", null);
            transactionsService.createWithdraw(transactionsDTO);
            return new ResponseEntity<>("Withdrawal successful", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to withdraw: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/repayment")
    public ResponseEntity<?> repayment(@RequestParam Long loanId, @RequestParam Double amount) {
        try {
            Long customerId = transactionsService.getCustomerIdByLoanId(loanId);
            TransactionsDTO transactionsDTO = new TransactionsDTO(customerId, amount, "repayment", loanId);
            transactionsService.createRepayment(transactionsDTO);
            return new ResponseEntity<>("Repayment successful", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to repay loan: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/allTransactions")
    public ResponseEntity<?> getAllTransactions() {
        try {
            return new ResponseEntity<>(transactionsService.getAllTransactions(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to get transactions: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getTransactionsByCustomerId(@PathVariable Long customerId) {
        try {
            return new ResponseEntity<>(transactionsService.getTransactionsByCustomerId(customerId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to get transactions: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateTransaction(@RequestParam Long transactionId, @RequestBody TransactionsDTO transactionsDTO) {
        try {
            Transactions updatedTransaction = transactionsService.updateTransaction(transactionId, transactionsDTO);
            return new ResponseEntity<>(updatedTransaction, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update transaction: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteTransaction(@RequestParam Long transactionId) {
        try {
            transactionsService.deleteTransaction(transactionId);
            return new ResponseEntity<>("Transaction deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete transaction: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}



