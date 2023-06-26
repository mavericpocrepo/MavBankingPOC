package com.mav.accountservice.exceptions;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String message) {
        super(message);
    }

    public AccountNotFoundException(String message, AccountNotFoundException e) {
        super(message);
    }

}