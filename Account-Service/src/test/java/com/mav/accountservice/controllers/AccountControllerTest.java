package com.mav.accountservice.controllers;

import com.mav.accountservice.entity.Account;
import com.mav.accountservice.serviceImpl.AccountServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


class AccountControllerTest {

    @Mock
    AccountServiceImpl accountServiceImpl;
    @InjectMocks
    AccountController accountController;
    protected MockMvc mockMvc;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc= MockMvcBuilders.standaloneSetup(accountController).build();
    }
    @Test
    void createAccount() {
        String customer_id = "12345";
        Account account=new Account();
        account.setAccount_Id(3245L);
        account.setCustomer_Id("BCE83C83d2A3");
        account.setCurrency("IND");
        account.setBalance("8000");
        account.setStatus("ACTIVE");
        account.setInterest_Rate("0.05");
        account.setOverdraft_Limit("2000");
        account.setPan_Number("DBOPM3668G");
        account.setAccount_Type("salary");

            Mockito.when(accountServiceImpl.createAccount(Mockito.any())).thenReturn(3245L);
            ResponseEntity<Object> response=accountController.createAccount(account);
            System.out.println(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(account.getAccount_Id(), response.getBody());
    }

    @Test
    void getAccountById() {
        String customer_id = "12345";
        Account account=new Account();
        account.setAccount_Id(3245L);
        account.setCustomer_Id("BCE83C83d2A3");
        account.setCurrency("IND");
        account.setBalance("8000");

        Mockito.when(accountServiceImpl.getAccountById(Mockito.anyLong())).thenReturn(Optional.of(account));
        ResponseEntity<Object> response=accountController.getAccountById(account.getAccount_Id());
        System.out.println("response"+response);
        Assertions.assertThat(response.getBody()).isEqualTo(account);

    }
    @Test
    void getAllAccounts() {
        String customer_id = "12345";
        Account account=new Account();
        account.setAccount_Id(3245L);
        account.setCustomer_Id("BCE83C83d2A3");
        account.setCurrency("IND");
        account.setBalance("8000");
        ArrayList<Account> records=new ArrayList<Account>();
        records.add(account);

         Mockito.when(accountServiceImpl.getAllAccounts()).thenReturn(records);
        ResponseEntity<List<Account>> response=accountController.getAllAccounts();
        System.out.println(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertThat(response.getBody()).isEqualTo(records);
        System.out.println("Account Controller"+accountController.toString());
    }
    @Test
    void updateAccountByCustomerId() {
        String customer_id = "12345";
        Account account=new Account();
        account.setAccount_Id(3245L);
        account.setCustomer_Id("BCE83C83d2A3");
        account.setCurrency("IND");
        account.setBalance("8000");


        Mockito.when(accountServiceImpl.updateAccount(Mockito.anyLong(),Mockito.any())).thenReturn("account updated successfully");
        ResponseEntity<String> response=accountController.updateAccountByCustomerId(account.getAccount_Id(),account);
        System.out.println(response);
        //assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("account updated successfully", response.getBody());

    }
    @Test
    void deleteAccount() {
        String customer_id = "12345";
        Account account=new Account();
        account.setAccount_Id(3245L);
        account.setCustomer_Id("BCE83C83d2A3");
        account.setCurrency("IND");
        account.setBalance("8000");

        Mockito.when(accountServiceImpl.deleteAccountByIds(Mockito.anyLong(),Mockito.any())).thenReturn(Optional.of(account));
        ResponseEntity<String> response=accountController.deleteAccount(account.getAccount_Id(), String.valueOf(account));
        System.out.println(response);
        assertEquals("Account deleted successfully", response.getBody());
    }

    @Test
    void getAccountDetailsByCustomerId() {
        String customer_id = "12345";
        Account account=new Account();
        account.setAccount_Id(3245L);
        account.setCustomer_Id("BCE83C83d2A3");
        account.setCurrency("IND");
        account.setBalance("8000");
        ArrayList<Account> records=new ArrayList<Account>();
        records.add(account);

        Mockito.when(accountServiceImpl.getAccountByCustomer_id(Mockito.any())).thenReturn(records);
        ResponseEntity<Object> response=accountController.getAccountDetailsByCustomerId(account.getCustomer_Id());
        System.out.println(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertThat(response.getBody()).isEqualTo(records);
        System.out.println("Account Controller"+accountController.toString());
    }
    @Test
    void updateAccount() {
        String customer_id = "12345";
        Account account=new Account();
        account.setAccount_Id(3245L);
        account.setCustomer_Id("BCE83C83d2A3");
        account.setCurrency("IND");
        account.setBalance("8000");
        ArrayList<Account> records=new ArrayList<Account>();
        records.add(account);

        Mockito.when(accountServiceImpl.updateAccountByField(Mockito.anyLong(),Mockito.any())).thenReturn(account);
        ResponseEntity<String> response=accountController.updateAccountByCustomerId(account.getAccount_Id(),account);
        System.out.println("response of updated account"+response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}