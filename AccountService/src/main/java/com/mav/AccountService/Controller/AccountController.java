package com.mav.AccountService.Controller;

import com.mav.AccountService.Model.AccountModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/account-api")
public class AccountController {
    @Autowired
    AccountModel accountModel;
    @GetMapping("/api")
    public ResponseEntity<AccountModel> create(){
    return ResponseEntity.status(HttpStatus.CREATED).body(accountModel);

    }


}
