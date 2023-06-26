package com.mav.CustomerService.service;

import com.mav.CustomerService.dto.CustomerDto;
import com.mav.CustomerService.model.Customer;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CustomerService {
    Customer saveCustomer(Customer customer);
    Optional<Customer> getCustomerById(Long customer_id);
    Optional<Customer> getCustomerByCustomer_id(String customer_id);
    List<Customer> getAllCustomers();
    void deleteCustomerById(String customer_id);
    boolean updateCustomer(CustomerDto updatedCustomer);

   // boolean updateCustomer(String customer_id, Customer updatedCustomer);

    String getCustomerIDBy_Pan_Number(CustomerDto updatedCustomer);

    Customer updateCustomerByField(String customerId, Map<String, Object> fields);
}
