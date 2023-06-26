package com.mav.accountservice.service;

import com.mav.accountservice.entity.Account;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AccountService {
    Optional<Account> getAccountById(Long customer_Id);

    List<Account> getAccountByCustomer_id(String customer_Id);

    List<Account> getAllAccounts();

    Optional<Account> deleteAccountByIds(Long account_Id, String customer_Id);

    String updateAccount(Long customer_Id, Account updatedAccount);

    Account updateAccountByField(Long account_Id, Map<String, Object> fields);

    Long createAccount(Account accountRequest);

}
