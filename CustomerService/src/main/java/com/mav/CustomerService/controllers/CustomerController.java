package com.mav.CustomerService.controllers;


import com.mav.CustomerService.dto.CustomerDto;
import com.mav.CustomerService.exceptions.CustomerCreationException;
import com.mav.CustomerService.exceptions.CustomerNotFoundException;
import com.mav.CustomerService.entity.Customer;
import com.mav.CustomerService.repo.CustomerRepository;
import com.mav.CustomerService.service.ApiService;
import com.mav.CustomerService.serviceImpl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api-v1")
public class CustomerController {
    @Autowired
    private CustomerServiceImpl customerServiceImpl;
    @Autowired
    private ApiService apiService;
    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/customer")
    public ResponseEntity<Object> createCustomer(@RequestBody CustomerDto customerRequest) {
        try {
            String customer_id = customerServiceImpl.createCustomer(customerRequest);
            return ResponseEntity.ok(customer_id);
        } catch (CustomerCreationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create customer: " + e.getMessage());
        }
    }

    @GetMapping("/customer/{customer_id}")
    public ResponseEntity<Object> getCustomerById(@PathVariable String customer_id) {
        try {
            Optional<Customer> customer = customerServiceImpl.getCustomerById(customer_id);
            if (customer.isPresent()) {
                return ResponseEntity.ok(customer.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            String errorMessage = "Error while retrieving customer with ID: " + customer_id;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorMessage);
        }
    }

    @GetMapping("/all-customers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        try {
            List<Customer> customers = customerServiceImpl.getAllCustomers();
            if (customers.isEmpty()) {
                return ResponseEntity.noContent().build(); // Return 204 No Content if no customers are found
            }
            return ResponseEntity.ok(customers); // Return the list of customers
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Return 500 Internal Server Error if an exception occurs
        }
    }

    @DeleteMapping("customer/{customer_id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable String customer_id) {
        try {
            Optional<Customer> customer = customerServiceImpl.deleteCustomer(customer_id);
            if (customer.isPresent()) {
                return ResponseEntity.ok("Customer deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting customer");
        }
    }

    @PutMapping("/updateCustomer")
    public ResponseEntity<String> updateCustomer(@RequestBody CustomerDto updatedCustomerDto) {
        boolean flag = customerServiceImpl.updateCustomer(updatedCustomerDto);
        if (flag)
            return ResponseEntity.ok("Customer updated successfully..");
        return ResponseEntity.badRequest().body("Failed to update customer..");
    }
    
    @PatchMapping("/updateCustomer/{customer_id}")
    public ResponseEntity<?> updateCustomer(@PathVariable String customer_id, @RequestBody Map<String, Object> fields) {
        try {
            Customer customer = customerServiceImpl.updateCustomerByField(customer_id, fields);
            return ResponseEntity.ok(customer);
        } catch (CustomerNotFoundException e) {
            String errorMessage = "Customer not found for ID: " + customer_id;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        } catch (Exception e) {
            String errorMessage = "Error while updating customer...";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @GetMapping("/mapping_customer")
    public String getCustomerIdByPanNumber(@RequestBody CustomerDto customerDto) {
        String customer_id = customerServiceImpl.getCustomerIDBy_Pan_Number(customerDto);
        return customer_id;
    }

}
