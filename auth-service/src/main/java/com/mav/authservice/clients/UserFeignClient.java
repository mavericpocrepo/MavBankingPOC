package com.mav.authservice.clients;

import com.mav.authservice.dto.UserDetailsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "user-service", url = "http://localhost:3015/api/users")
public interface UserFeignClient {
    @GetMapping("/userByEmail")
    UserDetailsResponse getUserByEmail(@RequestParam String email);
}
