package com.mav.accountservice.controllers;

import com.mav.accountservice.exceptions.AccountCreationException;
import com.mav.accountservice.exceptions.AccountNotFoundException;
import com.mav.accountservice.entity.Account;
import com.mav.accountservice.repo.AccountRepository;
import com.mav.accountservice.service.AccountApiService;
import com.mav.accountservice.serviceImpl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api-v1")
public class AccountController {
    @Autowired
    private AccountServiceImpl accountServiceImpl;
    @Autowired
    private AccountApiService accountApiService;
    @Autowired
    private AccountRepository accountRepository;

    @PostMapping("/account")
    public ResponseEntity<Object> createAccount(@RequestBody Account accountRequest) {
        try {
            Long account_id = accountServiceImpl.createAccount(accountRequest);
            return ResponseEntity.ok(account_id);
        } catch (AccountCreationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create account: " + e.getMessage());
        }
    }

    /**
     * Retrieves an Account by its account_Id.
     *
     * @return Optional<Account> The Account object if found, or an empty Optional.
     * @throws AccountNotFoundException if the Account with the given ID does not exist.
     */

    @GetMapping("/{account_Id}")
    public ResponseEntity<Object> getAccountById(@PathVariable Long account_Id) {
        try {
            Optional<Account> account = accountServiceImpl.getAccountById(account_Id);
            if (account==null) {
                String errorMessage = "No accounts found for the customer with account_Id: " + account_Id;
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
            }
            return ResponseEntity.ok(account.get());
        } catch (Exception e) {
            String errorMessage = "Error while retrieving account details with account_Id: " + account_Id;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);

        }
    }

    /**
     * Retrieves all accounts.
     *
     * @return List<Account> A list of all accounts.
     */
    @GetMapping("/all-accounts")
    public ResponseEntity<List<Account>> getAllAccounts() {
        try {
            List<Account> accounts = accountServiceImpl.getAllAccounts();
            if (accounts.isEmpty()) {
                return ResponseEntity.noContent().build(); // Return 204 No Content if no accounts are found
            }
            return ResponseEntity.ok(accounts); // Return the list of accounts
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Return 500 Internal Server Error if an exception occurs
        }
    }

    /**
     * Updates an account for a specific customer from his account ID.
     */
    @PutMapping("/{account_Id}/update")
    public ResponseEntity<String> updateAccountByCustomerId(@PathVariable("account_Id") Long account_Id, @RequestBody Account updatedAccount) {
        try {
            String result = accountServiceImpl.updateAccount(account_Id, updatedAccount);
            return ResponseEntity.ok(result);
        } catch (AccountNotFoundException e) {
            String errorMessage = "Account not found for ID: " + account_Id;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        } catch (Exception e) {
            String errorMessage = "Error updating account.";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @DeleteMapping("/account/{account_Id}/{customer_Id}")
    public ResponseEntity<String> deleteAccount(@PathVariable("account_Id") Long account_Id, @PathVariable("customer_Id") String customer_Id) {
        try {
            Optional<Account> account = accountServiceImpl.deleteAccountByIds(account_Id, customer_Id);
            if (account.isPresent()) {
                return ResponseEntity.ok("Account deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete account");
        }
    }

    @GetMapping("by/{customer_Id}")
    public ResponseEntity<Object> getAccountDetailsByCustomerId(@PathVariable String customer_Id) {
        try {
            List<Account> accounts = accountServiceImpl.getAccountByCustomer_id(customer_Id);
            if (accounts.isEmpty()) {
                String errorMessage = "No accounts found for the customer with ID: " + customer_Id;
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
            }
            return ResponseEntity.ok(accounts);
        } catch (Exception e) {
            String errorMessage = "Error retrieving account details for the customer with ID: " + customer_Id;
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    /**
     * Updates an Account entity by modifying specific fields based on the provided field-value pairs.
     *
     * @return ResponseEntity<Account> The response entity containing the updated Account entity.
     */
    @PatchMapping("/updateAccount/{account_Id}")
    public ResponseEntity<?> updateAccount(@PathVariable Long account_Id, @RequestBody Map<String, Object> fields) {
        try {
            Account account = accountServiceImpl.updateAccountByField(account_Id, fields);
            return ResponseEntity.ok(account);
        } catch (AccountNotFoundException e) {
            String errorMessage = "Account not found for ID: " + account_Id;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        } catch (Exception e) {
            String errorMessage = "Error updating account.";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }
}


