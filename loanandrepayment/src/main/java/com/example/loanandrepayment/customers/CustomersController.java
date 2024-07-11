package com.example.loanandrepayment.customers;

import com.example.loanandrepayment.DTOs.CustomersDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/customers")
public class CustomersController {
    private final CustomersService customersService;

    public CustomersController(CustomersService customersService) {
        this.customersService = customersService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCustomer(@RequestBody CustomersDTO customersDTO) {
        try {
            Customers createdCustomers = customersService.createCustomer(customersDTO);
            return new ResponseEntity<>(createdCustomers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create customer account: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllCustomers() {
        try {
            List<Customers> customers = customersService.getAllCustomers();
            return new ResponseEntity<>(customers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to get customers: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable("id") Long customerId) {
        try {
            Optional<Customers> customersOptional = customersService.getCustomerById(customerId);
            if (customersOptional.isPresent()) {
                return new ResponseEntity<>(customersOptional.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to get customer: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable("id") Long customerId, @RequestBody CustomersDTO customersDTO) {
        try {
            Customers updateCustomer = customersService.updateCustomer(customerId, customersDTO);
            return new ResponseEntity<>(updateCustomer, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update customer: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") Long customerId){
        try {
            customersService.deleteCustomer(customerId);
            return new ResponseEntity<>("Customer deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete customer: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
