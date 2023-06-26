package com.mav.CustomerService.controllers;


import com.mav.CustomerService.dto.CustomerDto;
import com.mav.CustomerService.exceptions.CustomerNotFoundException;
import com.mav.CustomerService.model.Customer;
import com.mav.CustomerService.repo.CustomerRepository;
import com.mav.CustomerService.service.ApiService;
import com.mav.CustomerService.serviceImpl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
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
    public String saveCustomerDetails(@RequestBody Customer customerRequest) {
        try {
            Customer customer = Customer.builder()
                    .id(customerRequest.getId())
                    .customer_id(customerRequest.generateCustomerId())
                    .first_Name(customerRequest.getFirst_Name())
                    .last_Name(customerRequest.getLast_Name())
                    .date_Of_Birth(customerRequest.getDate_Of_Birth())
                    .phone_number(customerRequest.getPhone_number())
                    .email(customerRequest.getEmail())
                    .pan_Number(customerRequest.getPan_Number())
                    .aadhaar(customerRequest.getAadhaar())
                    .street_name(customerRequest.getStreet_name())
                    .city(customerRequest.getCity())
                    .state(customerRequest.getState())
                    .zip_code(customerRequest.getZip_code())
                    .country(customerRequest.getCountry()).build();
            Customer customerResponse = customerServiceImpl.saveCustomer(customer);
            return new ResponseEntity<String>(customerResponse.getCustomer_id(), HttpStatus.CREATED).getBody();
        } catch (Exception e) {
            long longStatusCode = (long) HttpStatus.INTERNAL_SERVER_ERROR.value();
            return longStatusCode + "";
        }
    }

    @GetMapping("/customer/{id}")
    public Optional<Customer> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customer;
        try {
            customer = customerServiceImpl.getCustomerById(id);
            if (customer == null) {
                throw new CustomerNotFoundException("Customer with ID " + id + " does not exist.");
            }
        } catch (CustomerNotFoundException e) {
            throw new CustomerNotFoundException("Error retrieving customer with ID " + id);
        }
        return customer;
    }
    @GetMapping("/customer/by/{customer_id}")
    public Optional<Customer> getCustomerByCustomer_id(@PathVariable String customer_id) {
        try {
            Customer customer = customerRepository.findByCustomer_id(customer_id);
            if (customer == null) {
                throw new CustomerNotFoundException("Customer with ID " + customer_id + " does not exist.");
            }
        } catch (CustomerNotFoundException e) {
            throw new CustomerNotFoundException("Error retrieving customer with ID " + customer_id);
        }
        return customerServiceImpl.getCustomerByCustomer_id(customer_id);
    }

    @GetMapping("/all-customers")
    public List<Customer> getAllCustomers() {
        return customerServiceImpl.getAllCustomers();
    }

    @DeleteMapping("/{customer_id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable String customer_id) {
        try {
            Customer customer = customerRepository.findByCustomer_id(customer_id);
            if (customer == null) {
                throw new CustomerNotFoundException("Customer with ID " + customer_id + " does not exist.");
            }
            customerServiceImpl.deleteCustomerById(customer_id);
            return ResponseEntity.ok("Customer deleted successfully");

        } catch (CustomerNotFoundException e) {
            throw new CustomerNotFoundException("Error retrieving customer with ID " + customer_id);
        }

    }

    @PutMapping("/update")
    public ResponseEntity<String> updateCustomer(@RequestBody CustomerDto updatedCustomerDto) {
        boolean flage = customerServiceImpl.updateCustomer(updatedCustomerDto);
        if (flage)
            return ResponseEntity.ok("Customer updated successfully");
        return (ResponseEntity<String>) ResponseEntity.noContent();
    }

    @PatchMapping("/updateCustomer/{customer_id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable String customer_id,@RequestBody Map<String,Object> fields)
    {
        Customer customer =  customerServiceImpl.updateCustomerByField(customer_id,fields);
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/mapping_customer")
    public String getCustomerIdByPanNumber(@RequestBody CustomerDto customerDto) {
        String customer_id = customerServiceImpl.getCustomerIDBy_Pan_Number(customerDto);
        return customer_id;
    }

}
