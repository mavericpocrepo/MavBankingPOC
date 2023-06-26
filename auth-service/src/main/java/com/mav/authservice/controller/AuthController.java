package com.mav.authservice.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mav.authservice.config.JwtUtils;
import com.mav.authservice.dao.UserDao;
import com.mav.authservice.dto.AuthResponse;
import com.mav.authservice.dto.AuthenticationRequest;
import com.mav.authservice.dto.RequestDTO;
import com.mav.authservice.dto.UserDetailsResponse;
import feign.FeignException;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.function.ServerRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Api(tags = "auth-controller")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserDao userDao;
    private final JwtUtils jwtUtils;

    @PostMapping("/authenticate")
    @ApiOperation(value = "Authenticate", notes = "Authenticate a user")
    @Schema(description = "Example JWT token: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
    public ResponseEntity<?> authenticate(@RequestBody @ApiParam(value = "Authentication Request", required = true) AuthenticationRequest request)
    {

        try
        {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            final UserDetails user = userDao.findByEmail(request.getEmail());
            System.out.println("YASH "+user);
            AuthResponse authResponse = new AuthResponse();

            if (user != null) {
                authResponse.setToken(jwtUtils.generateToken(user));
                authResponse.setRole(user.getAuthorities().toString());
                return ResponseEntity.ok(authResponse);
            }
           // return ResponseEntity.status(400).body("Some Error has occurred");

        }
        catch (BadCredentialsException badCredentialsException)
        {
            throw new BadCredentialsException("Invalid username or password");
        }
         catch (InternalAuthenticationServiceException e)
        {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        catch (Exception e)
        {
            // Handle JSON parsing exception
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();

    }

    @PostMapping("/validateToken")
    @ApiOperation(value = "Validate Token", notes = "Validate JWT token")
    public ResponseEntity<?> validateToken(@RequestParam  @ApiParam(value = "JWT Token", required = true) String jwtToken) {
        try
        {
            System.out.println("Validatetoken method auth service " + jwtToken);
            String email = jwtUtils.extractUsername(jwtToken);
            UserDetails userDetails = userDao.findByEmail(email);
            System.out.println("Userdetails validate method " + userDetails);
            if (userDetails == null)
            {
                throw new BadCredentialsException("User Doesn't Exist");
            }
            boolean isTokenValid = jwtUtils.validateToken(jwtToken, userDetails);
            return ResponseEntity.ok(true);

        }
        catch (ExpiredJwtException e)
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
        catch (BadCredentialsException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (Exception ex)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

















//    @PostMapping("/validateToken")
//    public Boolean validateToken(@RequestParam String jwtToken)
//    {
//        System.out.println("Validatetoken method auth service "+jwtToken);
//        String email = jwtUtils.extractUsername(jwtToken);
//        UserDetails userDetails= userDao.findByEmail(email);
//        if(userDetails==null)
//        {
//            throw new BadCredentialsException("User Doesn't Exist");
//        }
//        boolean isTokenValid = jwtUtils.validateToken(jwtToken, userDetails);
//        if (!isTokenValid) {
//            return false;
//           // throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "JWT token expired");
//        }
//        return true;
//    }
}
