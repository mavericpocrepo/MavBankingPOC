package com.mav.UserService.controllers;

import com.mav.UserService.dto.UserResponse;
import com.mav.UserService.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.DateTimeAtProcessing;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/")
public class UsersController {
private final UserService userService;
    @GetMapping("/userById/{id}")
    public UserResponse getUserById(@PathVariable Long id) {return userService.findUserById(id);}

    @GetMapping("/userByEmail")
    public UserResponse getUserById(@RequestParam String email) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println("IN Users Service "+now);
        return userService.findUserByEmail(email);}

}
