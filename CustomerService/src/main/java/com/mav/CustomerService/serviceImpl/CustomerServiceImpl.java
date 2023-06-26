package com.mav.CustomerService.serviceImpl;

import com.mav.CustomerService.dto.CustomerDto;
import com.mav.CustomerService.exceptions.CustomerNotFoundException;
import com.mav.CustomerService.model.Customer;
import com.mav.CustomerService.repo.CustomerRepository;
import com.mav.CustomerService.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.Objects;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Optional<Customer> getCustomerById(Long id) {

        return customerRepository.findById(id);
    }

    @Override
    public Optional<Customer> getCustomerByCustomer_id(String customer_id) {
        return Optional.ofNullable(customerRepository.findByCustomer_id(customer_id));
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public void deleteCustomerById(String customer_id) {
        customerRepository.deleteByCustomerId(customer_id);
    }



    // below this done but need to converse with reflation code
    @Override
    public boolean updateCustomer(CustomerDto updatedCustomer) {
        if (Objects.isNull(updatedCustomer.getId()) || Objects.isNull(updatedCustomer.getCustomer_id())) {
            return false;
        }
        Customer customer = Customer.builder()
                .id(updatedCustomer.getId())
                .customer_id(updatedCustomer.getCustomer_id())
                .first_Name(updatedCustomer.getFirst_Name())
                .last_Name(updatedCustomer.getLast_Name())
                .date_Of_Birth(updatedCustomer.getDate_Of_Birth())
                .phone_number(updatedCustomer.getPhone_number())
                .email(updatedCustomer.getEmail())
                .pan_Number(updatedCustomer.getPan_Number())
                .aadhaar(updatedCustomer.getAadhaar())
                .street_name(updatedCustomer.getStreet_name())
                .city(updatedCustomer.getCity())
                .state(updatedCustomer.getState())
                .zip_code(updatedCustomer.getZip_code())
                .country(updatedCustomer.getCountry()).build();
        if (customer != null) {
            customerRepository.saveAndFlush(customer);
            return true;
        }
        return false;
    }
/*    @Override
    public boolean updateCustomer(String customer_id, Customer updatedCustomer) {
        Customer customer = customerRepository.findByCustomer_id(customer_id);
        if (customer != null) {
            customer.setCity(updatedCustomer.getCity());
            customer.setEmail(updatedCustomer.getEmail());
            customerRepository.save(customer);
            return true;
        }
        return false;
    }*/

    @Override
    public String getCustomerIDBy_Pan_Number(CustomerDto customerDto) {
        try {
            Optional<Customer> customer_objects = Optional.ofNullable(customerRepository.findByPanNumber(customerDto.getPan_Number()));
            if (customer_objects.isPresent()) {
                Customer customer = customer_objects.get();
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
        Optional<Customer> existingCustomer = Optional.ofNullable(customerRepository.findByCustomer_id(customer_id));
        if(existingCustomer.isPresent())
        {
            fields.forEach((key,value)->{
                Field field = ReflectionUtils.findField(Customer.class, key);
                field.setAccessible(true);
                ReflectionUtils.setField(field,existingCustomer.get(),value);
            });
        }
        return customerRepository.save(existingCustomer.get());
    }
}