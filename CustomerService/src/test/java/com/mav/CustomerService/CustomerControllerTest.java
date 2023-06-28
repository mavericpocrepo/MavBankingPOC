package com.mav.CustomerService;

import com.mav.CustomerService.controllers.CustomerController;
import com.mav.CustomerService.dto.CustomerDto;
import com.mav.CustomerService.entity.Customer;
import com.mav.CustomerService.exceptions.CustomerCreationException;
import com.mav.CustomerService.exceptions.CustomerNotFoundException;
import com.mav.CustomerService.serviceImpl.CustomerServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CustomerControllerTest {

    @Mock
    private CustomerServiceImpl customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCustomer_Success() {
        // Mock the customerServiceImpl.createCustomer() method
        String customer_id = "12345";
        when(customerService.createCustomer(any(CustomerDto.class))).thenReturn(customer_id);

        // Create a sample CustomerDto object for the request body
        CustomerDto customerRequest = new CustomerDto();
        // Set the necessary properties of the customerRequest object

        // Call the createCustomer() method in the controller
        ResponseEntity<Object> response = customerController.createCustomer(customerRequest);

        // Verify the response status code and the returned customer_id
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer_id, response.getBody());
    }

    @Test
    void testCreateCustomer_Exception() {
        // Mock the customerServiceImpl.createCustomer() method to throw a CustomerCreationException
        when(customerService.createCustomer(any(CustomerDto.class))).thenThrow(new CustomerCreationException("Test exception"));

        // Create a sample CustomerDto object for the request body
        CustomerDto customerRequest = new CustomerDto();
        // Set the necessary properties of the customerRequest object

        // Call the createCustomer() method in the controller
        ResponseEntity<Object> response = customerController.createCustomer(customerRequest);

        // Verify the response status code and the error message in the response body
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to create customer: Test exception", response.getBody());
    }

    @Test
    void testGetCustomerById_ExistingCustomer() {
        // Mock the customerServiceImpl.getCustomerById() method to return an Optional containing a Customer object
        Customer customer = new Customer();
        // Set the necessary properties of the customer object
        Optional<Customer> customerOptional = Optional.of(customer);
        when(customerService.getCustomerById(any(String.class))).thenReturn(customerOptional);

        // Call the getCustomerById() method in the controller
        ResponseEntity<Object> response = customerController.getCustomerById("12345");

        // Verify the response status code and the returned customer object
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer, response.getBody());
    }

    @Test
    void testGetCustomerById_NonexistentCustomer() {
        // Mock the customerServiceImpl.getCustomerById() method to return an empty Optional
        Optional<Customer> customerOptional = Optional.empty();
        when(customerService.getCustomerById(any(String.class))).thenReturn(customerOptional);

        // Call the getCustomerById() method in the controller
        ResponseEntity<Object> response = customerController.getCustomerById("12345");

        // Verify the response status code (should be 404 - Not Found)
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetCustomerById_Exception() {
        // Mock the customerServiceImpl.getCustomerById() method to throw an exception
        when(customerService.getCustomerById(any(String.class))).thenThrow(new RuntimeException());

        // Call the getCustomerById() method in the controller
        ResponseEntity<Object> response = customerController.getCustomerById("12345");

        // Verify the response status code (should be 500 - Internal Server Error) and the error message in the response body
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error while retrieving customer with ID: 12345", response.getBody());
    }


    @Test
    void testGetAllCustomers_EmptyList() {
        // Mock the customerServiceImpl.getAllCustomers() method to return an empty list
        List<Customer> customers = new ArrayList<>();
        when(customerService.getAllCustomers()).thenReturn(customers);

        // Call the getAllCustomers() method in the controller
        ResponseEntity<List<Customer>> response = customerController.getAllCustomers();

        // Verify the response status code (should be 204 - No Content)
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        // Verify the response body is not null and empty
        assertNull(response.getBody());
    }


    @Test
    void testGetAllCustomers_NonEmptyList() {
        // Mock the customerServiceImpl.getAllCustomers() method to return a list of customers
        List<Customer> customers = new ArrayList<>();
        // Add some Customer objects to the list
        customers.add(new Customer());
        customers.add(new Customer());
        when(customerService.getAllCustomers()).thenReturn(customers);

        // Call the getAllCustomers() method in the controller
        ResponseEntity<List<Customer>> response = customerController.getAllCustomers();

        // Verify the response status code (should be 200 - OK) and the returned list of customers
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customers, response.getBody());
    }



    @Test
    void testGetAllCustomers_Exception() {
        // Mock the customerServiceImpl.getAllCustomers() method to throw an exception
        when(customerService.getAllCustomers()).thenThrow(new RuntimeException());

        // Call the getAllCustomers() method in the controller
        ResponseEntity<List<Customer>> response = customerController.getAllCustomers();

        // Verify the response status code (should be 500 - Internal Server Error) and a null response body
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    void testDeleteCustomer_ExistingCustomer() {
        // Mock the customerServiceImpl.deleteCustomer() method to return an Optional with a customer
        Customer customer = new Customer();
        Optional<Customer> optionalCustomer = Optional.of(customer);
        when(customerService.deleteCustomer("customer_id")).thenReturn(optionalCustomer);

        // Call the deleteCustomer() method in the controller
        ResponseEntity<String> response = customerController.deleteCustomer("customer_id");

        // Verify the response status code (should be 200 - OK) and the response body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Customer deleted successfully", response.getBody());
    }

    @Test
    void testDeleteCustomer_NonExistingCustomer() {
        // Mock the customerServiceImpl.deleteCustomer() method to return an empty Optional
        Optional<Customer> optionalCustomer = Optional.empty();
        when(customerService.deleteCustomer("customer_id")).thenReturn(optionalCustomer);

        // Call the deleteCustomer() method in the controller
        ResponseEntity<String> response = customerController.deleteCustomer("customer_id");

        // Verify the response status code (should be 404 - Not Found) and the response body
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Customer not found", response.getBody());
    }

    @Test
    void testDeleteCustomer_Exception() {
        // Mock the customerServiceImpl.deleteCustomer() method to throw an exception
        when(customerService.deleteCustomer("customer_id")).thenThrow(new RuntimeException());

        // Call the deleteCustomer() method in the controller
        ResponseEntity<String> response = customerController.deleteCustomer("customer_id");

        // Verify the response status code (should be 500 - Internal Server Error) and the response body
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error deleting customer", response.getBody());
    }

    @Test
    void testUpdateCustomer_Success() {
        // Mock the customerServiceImpl.updateCustomerByField() method to return the updated customer
        Customer updatedCustomer = new Customer();
        when(customerService.updateCustomerByField(Mockito.anyString(), Mockito.anyMap())).thenReturn(updatedCustomer);

        // Create a sample customer ID and fields map for updating
        String customerId = "123";
        Map<String, Object> fields = new HashMap<>();
        // Add the fields to be updated in the map

        // Call the updateCustomer() method in the controller
        ResponseEntity<?> response = customerController.updateCustomer(customerId, fields);

        // Verify the response status code (should be 200 - OK) and the response body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedCustomer, response.getBody());

        // Verify that the customerServiceImpl.updateCustomerByField() method was called with the correct arguments
        verify(customerService, times(1)).updateCustomerByField(customerId, fields);
    }

    @Test
    void testUpdateCustomer_CustomerNotFound() {
        // Mock the customerServiceImpl.updateCustomerByField() method to throw a CustomerNotFoundException
        String customerId = "123";
        Map<String, Object> fields = new HashMap<>();
        when(customerService.updateCustomerByField(Mockito.anyString(), Mockito.anyMap()))
                .thenThrow(new CustomerNotFoundException("required customer with id "+customerId+" not found "));

        // Call the updateCustomer() method in the controller
        ResponseEntity<?> response = customerController.updateCustomer(customerId, fields);

        // Verify the response status code (should be 404 - Not Found) and the response body
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Customer not found for ID: " + customerId, response.getBody());

        // Verify that the customerServiceImpl.updateCustomerByField() method was called with the correct arguments
        verify(customerService, times(1)).updateCustomerByField(customerId, fields);
    }

    @Test
    void testUpdateCustomer_InternalServerError() {
        // Mock the customerServiceImpl.updateCustomerByField() method to throw an exception
        String customerId = "123";
        Map<String, Object> fields = new HashMap<>();
        when(customerService.updateCustomerByField(Mockito.anyString(), Mockito.anyMap()))
                .thenThrow(new RuntimeException());

        // Call the updateCustomer() method in the controller
        ResponseEntity<?> response = customerController.updateCustomer(customerId, fields);

        // Verify the response status code (should be 500 - Internal Server Error) and the response body
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error while updating customer...", response.getBody());

        // Verify that the customerServiceImpl.updateCustomerByField() method was called with the correct arguments
        verify(customerService, times(1)).updateCustomerByField(customerId, fields);
    }
}