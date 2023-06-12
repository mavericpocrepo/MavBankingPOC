package com.mav.UserService.controllers;

import com.mav.UserService.dto.UserResponse;
import com.mav.UserService.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/")
public class UsersController {
private final UserService userService;
    @GetMapping("/userById/{id}")
    public UserResponse getUserById(@PathVariable Long id) {return userService.findUserById(id);}

    @GetMapping("/userByEmail")
    public UserResponse getUserById(@RequestParam String email) {return userService.findUserByEmail(email);}

}
