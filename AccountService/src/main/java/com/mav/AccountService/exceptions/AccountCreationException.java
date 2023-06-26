package com.mav.accountservice.exceptions;

public class AccountCreationException extends RuntimeException {
    public AccountCreationException(String message) {
        super(message);
    }

    public AccountCreationException(String message, AccountCreationException e) {
        super(message);
    }
}