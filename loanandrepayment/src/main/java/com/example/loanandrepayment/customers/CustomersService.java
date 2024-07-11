package com.example.loanandrepayment.customers;

import com.example.loanandrepayment.DTOs.CustomersDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomersService {
    private final CustomersRepository customersRepository;

    public CustomersService(CustomersRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

    public Customers createCustomer(CustomersDTO customersDTO) {
        Customers customer = new Customers();
        customer.setName(customersDTO.name());
        customer.setCustomerId(customersDTO.customerId());
        customer.setEmail(customersDTO.email());
        customer.setBalance(customersDTO.balance());

        return customersRepository.save(customer);
    }

    public Optional<Customers> getCustomerById(Long customerId) {
        return customersRepository.findById(customerId);
    }

    public List<Customers> getAllCustomers() {
        return customersRepository.findAll();
    }

    public void deleteCustomer(Long id) {
        if (customersRepository.existsById(id)) {
            customersRepository.deleteById(id);
        } else {
            throw new RuntimeException("Customer ID not found");
        }
    }

    public Customers updateCustomer(Long id, CustomersDTO customersDTO){
        Optional<Customers> customerOptional = customersRepository.findById(id);
        if (customerOptional.isPresent()) {
            Customers existingCustomer = customerOptional.get();
            existingCustomer.setName(customersDTO.name());
            existingCustomer.setEmail(customersDTO.email());
            existingCustomer.setBalance(customersDTO.balance());
            return customersRepository.save(existingCustomer);
        } else {
            throw new RuntimeException("Customer not found");
        }
    }
}