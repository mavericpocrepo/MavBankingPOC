package com.mav.authservice.controller;

import com.mav.authservice.config.JwtUtils;
import com.mav.authservice.dao.UserDao;
import com.mav.authservice.dto.AuthenticationRequest;
import com.mav.authservice.dto.UserDetailsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserDao userDao;
    private final JwtUtils jwtUtils;

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest request) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            final UserDetails user = userDao.findByEmail(request.getEmail());
            System.out.println("YASH "+user);
            if (user != null) {
                return ResponseEntity.ok(jwtUtils.generateToken(user));
            }
            return ResponseEntity.status(400).body("Some Error has occurred");

        }
        catch (BadCredentialsException badCredentialsException)
        {
            throw new BadCredentialsException("Invalid username or password");
        }

    }

    @PostMapping("/validateToken")
    public ResponseEntity<?> validateToken(@RequestParam String jwtToken) {
        try {
            System.out.println("Validatetoken method auth service " + jwtToken);
            String email = jwtUtils.extractUsername(jwtToken);
            UserDetails userDetails = userDao.findByEmail(email);
            if (userDetails == null) {
                throw new BadCredentialsException("User Doesn't Exist");
            }
            boolean isTokenValid = jwtUtils.validateToken(jwtToken, userDetails);
            if (!isTokenValid) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "JWT token expired");
            }
            return ResponseEntity.ok(true);
        //}
//        catch ( ex) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("JWT token expired");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Expired JWT Token");
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
