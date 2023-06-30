package com.mav.CustomerService;

import com.mav.CustomerService.dto.CustomerDto;
import com.mav.CustomerService.entity.Customer;
import com.mav.CustomerService.exceptions.CustomerNotFoundException;
import com.mav.CustomerService.repo.CustomerRepository;
import com.mav.CustomerService.serviceImpl.CustomerServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCustomer() {
        CustomerDto customerDto = new CustomerDto();
        Customer customer = new Customer();
        when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(customer);
        String customerId = customerService.createCustomer(customerDto);
        assertEquals(customer.getCustomer_id(), customerId);
        verify(customerRepository, times(1)).save(Mockito.any(Customer.class));
    }


    @Test
    public void testGetCustomerById_Exists() {
        String customerId = "123";
        Customer customer = new Customer();
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        Optional<Customer> result = customerService.getCustomerById(customerId);
        assertTrue(result.isPresent());
        assertEquals(customer, result.get());
        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    public void testGetCustomerById_NotExists() {
        String customerId = "456";
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());
        Optional<Customer> result = customerService.getCustomerById(customerId);
        assertFalse(result.isPresent());
        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    public void testGetAllCustomers_EmptyList() {
        List<Customer> customers = new ArrayList<>();
        when(customerRepository.findAll()).thenReturn(customers);
        List<Customer> result = customerService.getAllCustomers();
        assertTrue(result.isEmpty());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllCustomers_NonEmptyList() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer());
        when(customerRepository.findAll()).thenReturn(customers);
        List<Customer> result = customerService.getAllCustomers();
        assertEquals(customers.size(), result.size());
        assertEquals(customers.get(0), result.get(0));
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteCustomer_CustomerExists() {
        String customerId = "123";
        Customer customer = new Customer();
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        Optional<Customer> result = customerService.deleteCustomer(customerId);
        assertTrue(result.isPresent());
        assertEquals(customer, result.get());
        verify(customerRepository, times(1)).findById(customerId);
        verify(customerRepository, times(1)).deleteById(customerId);
    }

    @Test
    public void testDeleteCustomer_CustomerDoesNotExist() {
        String customerId = "123";
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());
        Optional<Customer> result = customerService.deleteCustomer(customerId);
        assertFalse(result.isPresent());
        verify(customerRepository, times(1)).findById(customerId);
        verify(customerRepository, never()).deleteById(customerId);
    }

    @Test
    public void testUpdateCustomer_CustomerExists() {
        String customerId = "123";
        CustomerDto updatedCustomerDto = new CustomerDto();
        updatedCustomerDto.setCustomer_id(customerId);
        Customer existingCustomer = new Customer();
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomer));
        boolean result = customerService.updateCustomer(updatedCustomerDto);
        assertTrue(result);
        verify(customerRepository, times(1)).findById(customerId);
        verify(customerRepository, times(1)).saveAndFlush(existingCustomer);
    }

    @Test
    public void testUpdateCustomer_CustomerDoesNotExist() {
        String customerId = "123";
        CustomerDto updatedCustomerDto = new CustomerDto();
        updatedCustomerDto.setCustomer_id(customerId);
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> customerService.updateCustomer(updatedCustomerDto));
        verify(customerRepository, times(1)).findById(customerId);
        verify(customerRepository, never()).saveAndFlush(any(Customer.class));
    }

    @Test
    public void testUpdateCustomer_MissingCustomerId() {
        CustomerDto updatedCustomerDto = new CustomerDto();
        assertThrows(IllegalArgumentException.class, () -> customerService.updateCustomer(updatedCustomerDto));
        verify(customerRepository, never()).findById(anyString());
        verify(customerRepository, never()).saveAndFlush(any(Customer.class));
    }


    @Test
    public void testGetCustomerIDBy_Pan_Number_CustomerExists() {
        // Arrange
        String panNumber = "ABCD1234";
        CustomerDto customerDto = new CustomerDto();
        customerDto.setPan_Number(panNumber);
        Customer existingCustomer = new Customer();
        existingCustomer.setCustomer_id("123");
        when(customerRepository.findByPanNumber(panNumber)).thenReturn(existingCustomer);
        String customerId = customerService.getCustomerIDBy_Pan_Number(customerDto);
        assertEquals(existingCustomer.getCustomer_id(), customerId);
        verify(customerRepository, times(1)).findByPanNumber(panNumber);
    }

    @Test
    public void testGetCustomerIDBy_Pan_Number_CustomerDoesNotExist() {
        String panNumber = "ABCD1234";
        CustomerDto customerDto = new CustomerDto();
        customerDto.setPan_Number(panNumber);
        when(customerRepository.findByPanNumber(panNumber)).thenReturn(null);
        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomerIDBy_Pan_Number(customerDto));
        verify(customerRepository, times(1)).findByPanNumber(panNumber);
    }

    @Test
    public void testUpdateCustomerByField_CustomerExists() {
        // Arrange
        String customerId = "123";
        Map<String, Object> fields = new HashMap<>();
        fields.put("first_Name", "John");
        fields.put("last_Name", "Doe");
        Customer existingCustomer = new Customer();
        existingCustomer.setCustomer_id(customerId);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(existingCustomer)).thenReturn(existingCustomer);

        // Act
        Customer updatedCustomer = customerService.updateCustomerByField(customerId, fields);

        // Assert
        assertNotNull(updatedCustomer);
        assertEquals("John", updatedCustomer.getFirst_Name());
        assertEquals("Doe", updatedCustomer.getLast_Name());

        verify(customerRepository, times(1)).findById(customerId);
        verify(customerRepository, times(1)).save(existingCustomer);
    }
    @Test
    public void testUpdateCustomerByField_CustomerDoesNotExist() {
        String customerId = "123";
        Map<String, Object> fields = new HashMap<>();
        fields.put("first_Name", "John");
        fields.put("last_Name", "Doe");
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());
        // Act & Assert
        assertThrows(CustomerNotFoundException.class, () -> customerService.updateCustomerByField(customerId, fields));
        verify(customerRepository, times(1)).findById(customerId);
        verify(customerRepository, never()).save(any());
    }
}