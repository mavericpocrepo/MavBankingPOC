package com.example.demo.clients;

import com.example.demo.dto.EmployeeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value="user-service",url = "http://localhost:3015/api/users")
public interface ServiceClients {
    @GetMapping("/userById/{id}")
    EmployeeResponse getUserByEmail(@PathVariable Long id);

}
