package com.mav.CustomerService.exceptions;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String message) {
        super(message);
    }
    public CustomerNotFoundException(String message,CustomerNotFoundException e ) {

        super(message);
    }
}