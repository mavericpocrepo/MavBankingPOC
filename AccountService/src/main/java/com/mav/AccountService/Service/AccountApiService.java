package com.mav.accountservice.service;

import com.mav.accountservice.dto.CustomerDto;
import com.mav.accountservice.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountApiService {
    private final RestTemplate restTemplate;

    @Autowired
    public AccountApiService(RestTemplate restTemplate) {

        this.restTemplate = restTemplate;
    }

    public String getCustomerIdByPanNumber(Account accountResponse) {
        String customerApiUrl = "http://localhost:3005/api-v1/mapping_customer";
        CustomerDto customerRequestObj = CustomerDto.builder().pan_Number(accountResponse.getPan_Number()).build();
        ResponseEntity<String> customer_Id = restTemplate.exchange(customerApiUrl, HttpMethod.PUT, new HttpEntity<>(customerRequestObj), String.class);

        return customer_Id.getBody();
    }
}
