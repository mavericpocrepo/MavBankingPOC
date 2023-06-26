package com.mav.CustomerService.exceptions;

public class CustomerCreationException extends RuntimeException {
    public CustomerCreationException(String message) {
        super(message);
    }
    public CustomerCreationException(String message, CustomerCreationException e) {
        super(message);
    }
}