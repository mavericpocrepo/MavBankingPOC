package com.mav.CustomerService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class ApiService {
   private final RestTemplate restTemplate;

    @Autowired
    public ApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

/*    public Account callAccountService(String id) {
        String accountApiBaseUrl = "";
        Account accountResponse = restTemplate.getForObject(" BaseURL", Account.class);
        return accountResponse;
    }*/
}
