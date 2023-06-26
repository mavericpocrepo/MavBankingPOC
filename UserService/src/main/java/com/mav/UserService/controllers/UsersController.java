package com.mav.UserService.controllers;

import com.mav.UserService.dto.UserResponse;
import com.mav.UserService.model.Users;
import com.mav.UserService.service.UserService;
import lombok.RequiredArgsConstructor;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.DateTimeAtProcessing;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/")
public class UsersController {
private final UserService userService;
    @GetMapping("/userById/{id}")
    public UserResponse getUserById(@PathVariable Long id) {return userService.findUserById(id);}

    @GetMapping("/userByEmail")
    public ResponseEntity<?> getUserById(@RequestParam String email)
    {
        try
        {
            return ResponseEntity.ok(userService.findUserByEmail(email));
        }
        catch (Exception e)
        {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
        }

    }

    @PatchMapping("/updateUser/{id}")
    public ResponseEntity<?> updateUser(@PathVariable long id,@RequestBody Map<String,Object> fields)
    {
        try
        {
            Users user =  userService.updateUserByField(id,fields);
            if(user!=null)
            return ResponseEntity.ok(user);
            else
                throw new NoSuchElementException();
        }
        catch (NoSuchElementException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("An Error Occured");
        }

    }

}
