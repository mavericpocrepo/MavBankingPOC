package com.mav.UserService.service;

import com.mav.UserService.dto.UserResponse;
import com.mav.UserService.model.Users;
import com.mav.UserService.repo.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.NoSuchElementException;
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


    public Users updateUserByField(long id, Map<String, Object> fields) {
        try {
            Optional<Users> existingUser = usersRepository.findById(id);
            if(existingUser.isPresent())
            {
                fields.forEach((key,value)->{
                    Field field = ReflectionUtils.findField(Users.class, key);
                    field.setAccessible(true);
                    ReflectionUtils.setField(field,existingUser.get(),value);
                });
            }
            return usersRepository.save(existingUser.get());

        }
        catch (NoSuchElementException e)
        {
            System.out.println(e);
            throw new NoSuchElementException("No User Found");
        }

    }
}
