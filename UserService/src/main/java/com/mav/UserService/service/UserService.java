package com.mav.UserService.service;

import com.mav.UserService.dto.UserResponse;
import com.mav.UserService.model.Users;
import com.mav.UserService.repo.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UsersRepository usersRepository;

    public UserResponse findUserById(Long id)
    {
       Optional<Users> users= usersRepository.findById(id);
       return UserResponse.builder()
               .userId(users.get().getUserId())
               .email(users.get().getEmail())
               .password(users.get().getPassword())
               .role(users.get().getRole())
               .build();


    }

    public UserResponse findUserByEmail(String email)
    {
        Users users= usersRepository.findByEmail(email);

        return UserResponse.builder()
                .userId(users.getUserId())
                .email(users.getEmail())
                .password(users.getPassword())
                .role(users.getRole())
                .build();


    }
}
