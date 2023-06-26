package com.mav.authservice.dao;

import com.mav.authservice.clients.UserFeignClient;
import com.mav.authservice.dto.UserDetailsResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class UserDao {

    private final RestTemplate restTemplate;
private final UserFeignClient userFeignClient;

    public UserDetails findByEmail(String email)
    {
//        String userUrl = "http://localhost:8080/api/users/userByEmail?email=" + email;
//        ResponseEntity<UserDetailsResponse> response = restTemplate.getForEntity(userUrl, UserDetailsResponse.class);
//        System.out.println("YASHASWI "+response.getBody());
//        return new User(
//                response.getBody().getEmail(),
//                response.getBody().getPassword(),
//                getAuthorities(response.getBody().getRole())
//
//                );
        try
        {
            UserDetailsResponse user = userFeignClient.getUserByEmail(email);
            return new  User(user.getEmail(),
                        user.getPassword(),
                        getAuthorities(user.getRole()));
        }
        catch (FeignException.InternalServerError e)
        {
            throw new BadCredentialsException("No User found");
        }

//        UserDetails dummyUserDetails = new org.springframework.security.core.userdetails.User(
//                "yash@g.com",
//                "yash",
//                getAuthorities("ADMIN")
//        );
//        return dummyUserDetails;

    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

}
