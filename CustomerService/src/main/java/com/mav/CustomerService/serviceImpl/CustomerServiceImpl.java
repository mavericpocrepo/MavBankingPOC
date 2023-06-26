package com.mav.CustomerService.serviceImpl;

import com.mav.CustomerService.dto.CustomerDto;
import com.mav.CustomerService.exceptions.CustomerNotFoundException;
import com.mav.CustomerService.entity.Customer;
import com.mav.CustomerService.repo.CustomerRepository;
import com.mav.CustomerService.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.Objects;


public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public String createCustomer(CustomerDto customerRequest) {
        try {
            Customer customer = Customer.builder().customer_id(generateCaseId()).first_Name(customerRequest.getFirst_Name()).last_Name(customerRequest.getLast_Name()).date_Of_Birth(customerRequest.getDate_Of_Birth()).phone_Number(customerRequest.getPhone_number()).email(customerRequest.getEmail()).pan_Number(customerRequest.getPan_Number()).aadhaar(customerRequest.getAadhaar()).street_Name(customerRequest.getStreet_name()).city(customerRequest.getCity()).state(customerRequest.getState()).zip_Code(customerRequest.getZip_code()).country(customerRequest.getCountry()).build();
            Customer customerCreatedResponse = customerRepository.save(customer);
            return customerCreatedResponse.getCustomer_id();
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR.value() + " INTERNAL_SERVER_ERROR";
        }
    }


    private String generateCaseId() {
        UUID uuid = UUID.randomUUID();
        String caseId = uuid.toString().toUpperCase().replaceAll("-", "");
        return "BC" + caseId.substring(0, 8); // Using first 8 characters of UUID
    }


    @Override
    public Optional<Customer> getCustomerById(String customer_id) {
        return customerRepository.findById(customer_id);
    }


    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> deleteCustomer(String customer_Id) {
        Optional<Customer> customer = customerRepository.findById(customer_Id);
        if (customer.isPresent()) {
            customerRepository.deleteById(customer_Id);
        }
        return customer;
    }

    // below this done but need to converse with reflation code
    @Override
    public boolean updateCustomer(CustomerDto updatedCustomer) {
        if (Objects.isNull(updatedCustomer.getCustomer_id())) {
            throw new IllegalArgumentException("Customer ID is required.");
        }
        Optional<Customer> existingCustomer = customerRepository.findById(updatedCustomer.getCustomer_id());
        if (existingCustomer.isPresent()) {
            Customer customer = existingCustomer.get();
            customer.setFirst_Name(updatedCustomer.getFirst_Name());
            customer.setLast_Name(updatedCustomer.getLast_Name());
            customer.setDate_Of_Birth(updatedCustomer.getDate_Of_Birth());
            customer.setPhone_Number(updatedCustomer.getPhone_number());
            customer.setEmail(updatedCustomer.getEmail());
            customer.setPan_Number(updatedCustomer.getPan_Number());
            customer.setAadhaar(updatedCustomer.getAadhaar());
            customer.setStreet_Name(updatedCustomer.getStreet_name());
            customer.setCity(updatedCustomer.getCity());
            customer.setState(updatedCustomer.getState());
            customer.setZip_Code(updatedCustomer.getZip_code());
            customer.setCountry(updatedCustomer.getCountry());
            customerRepository.saveAndFlush(customer);
            return true;
        } else {
            throw new NoSuchElementException("Customer with ID " + updatedCustomer.getCustomer_id() + " does not exist.");
        }
    }

    @Override
    public String getCustomerIDBy_Pan_Number(CustomerDto customerDto) {
        try {
            Optional<Customer> db_customer = Optional.ofNullable(customerRepository.findByPanNumber(customerDto.getPan_Number()));
            if (db_customer.isPresent()) {
                Customer customer = db_customer.get();
                return customer.getCustomer_id();
            } else {
                throw new CustomerNotFoundException("Customer not found for PAN number: " + customerDto.getPan_Number());
            }
        } catch (CustomerNotFoundException e) {
            throw new CustomerNotFoundException("while fetching customer not found " + e);
        }
    }

    @Override
    public Customer updateCustomerByField(String customer_id, Map<String, Object> fields) {
        try {
            Optional<Customer> existingCustomer = customerRepository.findById(customer_id);
            if (existingCustomer.isPresent()) {
                fields.forEach((key, value) -> {
                    Field field = ReflectionUtils.findField(Customer.class, key);
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, existingCustomer.get(), value);
                });
                return customerRepository.save(existingCustomer.get());
            } else {
                throw new CustomerNotFoundException("Customer with ID " + customer_id + " does not exist.");
            }
        } catch (CustomerNotFoundException e) {
            throw new CustomerNotFoundException("Error while updating customer: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error while updating customer.", e);
        }
    }
}