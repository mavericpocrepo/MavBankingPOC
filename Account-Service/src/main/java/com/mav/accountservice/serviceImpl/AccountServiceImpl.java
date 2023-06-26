package com.mav.accountservice.serviceImpl;


import com.mav.accountservice.entity.Account;
import com.mav.accountservice.exceptions.AccountCreationException;
import com.mav.accountservice.exceptions.AccountNotFoundException;
import com.mav.accountservice.repo.AccountRepository;
import com.mav.accountservice.service.AccountApiService;
import com.mav.accountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountApiService accountApiService;

    public Long createAccount(Account accountRequest) throws AccountCreationException {
        try {
            Account account = Account.builder()
                    .account_Type(accountRequest.getAccount_Type())
                    .customer_Id(accountRequest.getCustomer_Id())
                    .pan_Number(accountRequest.getPan_Number())
                    .balance(accountRequest.getBalance())
                    .currency(accountRequest.getCurrency())
                    .status(accountRequest.getStatus())
                    .interest_Rate(accountRequest.getInterest_Rate())
                    .overdraft_Limit(accountRequest.getOverdraft_Limit())
                    .c_Date(new Date())
                    .u_B_User("Mithlesh")// need to change at runtine user
                    .l_U_Date(new Date())
                    .build();
            /*String customer_id = String.valueOf(accountApiService.getCustomerIdByPanNumber(account)); */ //uncomment to indirect with customer api
            Account createdAccountResponse = accountRepository.save(account);
            return createdAccountResponse.getAccount_Id();
        } catch (AccountCreationException e) {
            throw new AccountCreationException("Failed to create account.", e);
        }
    }

    @Override
    public Optional<Account> getAccountById(Long account_Id) {
        return accountRepository.findById(account_Id);
    }

    @Override
    public List<Account> getAccountByCustomer_id(String customer_Id) {
        return accountRepository.findAccountByCustomer_id(customer_Id);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Optional<Account> deleteAccountByIds(Long account_Id, String customer_Id) {
        Optional<Account> account = accountRepository.findById(account_Id);
        if (account.isPresent()) {
            accountRepository.deleteAccountByIds(account_Id, customer_Id);
        }
        return account;
    }

    @Override
    public String updateAccount(Long account_Id, Account updatedAccount) {
        try {
            Optional<Account> accounts = accountRepository.findById(account_Id);
            if (accounts.isPresent()) {
                Account account = accounts.get();
                account.setAccount_Type(updatedAccount.getAccount_Type());
                account.setCustomer_Id(updatedAccount.getCustomer_Id());
                account.setPan_Number(updatedAccount.getPan_Number());
                account.setBalance(updatedAccount.getBalance());
                account.setCurrency(updatedAccount.getCurrency());
                account.setStatus(updatedAccount.getStatus());
                account.setInterest_Rate(updatedAccount.getInterest_Rate());
                account.setOverdraft_Limit(updatedAccount.getOverdraft_Limit());
                accountRepository.save(account);
                return "Account updated successfully";
            } else {
                throw new NoSuchElementException("Account not found for ID: " + account_Id);
            }
        } catch (NoSuchElementException e) {
            String errorMessage = "Failed to update account. " + e.getMessage();
            // Handle the NoSuchElementException and return the informative message
            return errorMessage;
        } catch (Exception e) {
            String errorMessage = "Failed to update account due to an unexpected error.";
            // Handle any other exception and return the informative message
            return errorMessage;
        }
    }

    /**
     * Updates an Account entity by modifying specific fields based on the provided field-value pairs.s
     *
     * @return Account The updated Account entity.
     */

    @Override
    public Account updateAccountByField(Long account_Id, Map<String, Object> fields) throws AccountNotFoundException {
        Optional<Account> existingAccount = accountRepository.findById(account_Id);
        if (existingAccount.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Account.class, key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, existingAccount.get(), value);
            });
            return accountRepository.save(existingAccount.get());
        } else {
            throw new AccountNotFoundException("Account with ID " + account_Id + " not found.");
        }
    }
}

