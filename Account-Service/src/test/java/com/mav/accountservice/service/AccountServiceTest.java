package com.mav.accountservice.service;

import com.mav.accountservice.entity.Account;
import com.mav.accountservice.repo.AccountRepository;
import com.mav.accountservice.serviceImpl.AccountServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountServiceTest {

    @Mock
    AccountRepository accountRepository;
    @InjectMocks
    AccountServiceImpl accountServiceimpl;

    @Test
    void getAccountById() {
        Account account=new Account();
        account.setAccount_Id(3245L);
        account.setAccount_Type("salary");
        account.setCustomer_Id("BCE83C83d2A3");
        account.setCurrency("IND");
        account.setBalance("8000");
        account.setStatus("ACTIVE");
        account.setInterest_Rate("0.05");
        account.setOverdraft_Limit("2000");
        account.setPan_Number("DBOPM3668G");


        ArrayList<Account> records=new ArrayList<Account>();
        records.add(account);
        Mockito.when(accountRepository.findById(Mockito.anyLong())).thenReturn(records.stream().findAny());
        Optional<Account> result=accountServiceimpl.getAccountById(3245L);
        Mockito.verify(accountRepository).findById(3245L);
        Assertions.assertNotNull(result);
        Mockito.verify(accountRepository).findById(3245L);
        System.out.println(result);
    }
    @Test
    void getAccountByCustomer_id() {
        Account account=new Account();
        account.setAccount_Id(3245L);
        account.setAccount_Type("salary");
        account.setCustomer_Id("BCE83C83d2A3");
        account.setCurrency("IND");
        account.setBalance("8000");
        account.setStatus("ACTIVE");
        account.setInterest_Rate("0.05");

        ArrayList<Account> records=new ArrayList<Account>();
        records.add(account);
        Mockito.when(accountRepository.findAccountByCustomer_id("BCE83C83d2A3")).thenReturn(records);
        Iterable<Account> result=accountServiceimpl.getAccountByCustomer_id("BCE83C83d2A3");
        Mockito.verify(accountRepository).findAccountByCustomer_id("BCE83C83d2A3");
        Assertions.assertNotNull(result);
        Mockito.verify(accountRepository).findAccountByCustomer_id("BCE83C83d2A3");
        System.out.println(result);
    }

    @Test
    void getAllAccounts() {

        Account account=new Account();
        account.setAccount_Id(3245L);
        account.setAccount_Type("salary");
        account.setCustomer_Id("BCE83C83d2A3");
        account.setCurrency("IND");
        account.setBalance("8000");
        account.setStatus("ACTIVE");
        account.setInterest_Rate("0.05");
        account.setOverdraft_Limit("2000");
        account.setPan_Number("DBOPM3668G");

        ArrayList<Account> records=new ArrayList<Account>();
        records.add(account);
        Mockito.when(accountRepository.findAll()).thenReturn(records);
        Iterable<Account> result=accountServiceimpl.getAllAccounts();
        Mockito.verify(accountRepository).findAll();
        Assertions.assertNotNull(result);
        Mockito.verify(accountRepository).findAll();
        System.out.println(result);
    }

    @Test
    void deleteAccountByIds() {
        Account account=new Account();
        account.setAccount_Id(3245L);
        account.setAccount_Type("salary");
        account.setCustomer_Id("BCE83C83d2A3");
        account.setCurrency("IND");
        account.setBalance("8000");
        account.setStatus("ACTIVE");
        account.setInterest_Rate("0.05");
        account.setOverdraft_Limit("2000");
        account.setPan_Number("DBOPM3668G");

        Mockito.when(accountRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(account));
        Mockito.doNothing().when(accountRepository).deleteAccountByIds(account.getAccount_Id(), account.getCustomer_Id());
        accountRepository.deleteAccountByIds(3245L,"BCE83C83d2A3");
        Mockito.verify(accountRepository).deleteAccountByIds(3245L,"BCE83C83d2A3");
    }

    @Test
    void updateAccount() {
        Account account=new Account();
        account.setAccount_Id(3245L);
        account.setAccount_Type("salary");
        account.setCustomer_Id("BCE83C83d2A3");
        account.setCurrency("IND");
        account.setBalance("8000");
        account.setStatus("ACTIVE");
        account.setInterest_Rate("0.05");
        account.setOverdraft_Limit("2000");
        account.setPan_Number("DBOPM3668G");

        Mockito.when(accountRepository.save(account)).thenReturn(account);
        Mockito.when(accountRepository.findById(3245L)).thenReturn(Optional.of(account));
        Mockito.when(accountServiceimpl.updateAccount(3245L,account)).thenReturn(String.valueOf(account));
        String result=accountServiceimpl.updateAccount(3245L,account);
        Mockito.verify(accountRepository).save(Mockito.any());
        Assertions.assertNotNull(result);
        Assertions.assertEquals("DBOPM3668G",account.getPan_Number());
    }

    @Test
    void updateAccountByField() {
        Account account=new Account();
        account.setAccount_Id(3245L);
        account.setAccount_Type("salary");
        account.setCustomer_Id("BCE83C83d2A3");
        account.setCurrency("IND");
         account.setPan_Number("DBOPM3668G");

       Map<String,Account> map=new HashMap<String,Account>();

       /*map.entrySet().add((Map.Entry<String, Account>) account);*/
       Mockito.when(accountRepository.save(account)).thenReturn(account);
       Mockito.when(accountRepository.findById(3245L)).thenReturn(Optional.of(account));

       String result=accountServiceimpl.updateAccount(3245L,account);
       Mockito.verify(accountRepository).save(Mockito.any());
       Assertions.assertNotNull(result);
       Assertions.assertEquals("DBOPM3668G",account.getPan_Number());
    }

    @Test
    void createAccount() {
        Account account=new Account();
        account.setAccount_Id(3245L);
        account.setAccount_Type("salary");
        account.setCustomer_Id("BCE83C83d2A3");
        account.setCurrency("IND");
        account.setBalance("8000");
        account.setStatus("ACTIVE");
        account.setInterest_Rate("0.05");
        account.setOverdraft_Limit("2000");
        account.setPan_Number("DBOPM3668G");

        Mockito.when(accountRepository.save(Mockito.any())).thenReturn(account);
        Long result=accountServiceimpl.createAccount(account);
        Mockito.verify(accountRepository).save(Mockito.any());
        Assertions.assertNotNull(result);
        Assertions.assertEquals("salary",account.getAccount_Type(),"account is created");
    }
}
