package com.mav.CustomerService.service;

import com.mav.CustomerService.dto.CustomerDto;
import com.mav.CustomerService.entity.Customer;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CustomerService {
    Optional<Customer> getCustomerById(String customer_id);

    List<Customer> getAllCustomers();

    boolean updateCustomer(CustomerDto updatedCustomer);

    String getCustomerIDBy_Pan_Number(CustomerDto updatedCustomer);

    Customer updateCustomerByField(String customerId, Map<String, Object> fields);

    String createCustomer(CustomerDto customerRequest);

    Optional<Customer> deleteCustomer(String customerId);
}
