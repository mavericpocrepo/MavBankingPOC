package com.mav.AccountService.Service;

import com.mav.AccountService.Model.AccountModel;

public interface AccountService {
public AccountModel create(AccountModel accountModel);
public void delete(Integer account_Id);
public AccountModel fetchAccountModel(Integer account_Id);
public AccountModel updateAccountModel(Integer account_Id,Integer user_id);
}

//save,