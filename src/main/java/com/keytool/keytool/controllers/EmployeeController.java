package com.keytool.keytool.controllers;

import com.keytool.keytool.config.JwtUtil;
import com.keytool.keytool.entity.User;
import com.keytool.keytool.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class EmployeeController {
    @Autowired
    private JwtUtil jwtUtil ;

    private final UserService  userService;

    public EmployeeController( UserService userService) {

        this.userService = userService;
    }


    @GetMapping("/profile")
    public ResponseEntity<User> viewProfile(@RequestParam String email, Authentication authentication) {

        String loggedInEmail = authentication.getName();



        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !email.equals(loggedInEmail)) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }


        User user = userService.viewProfile(email);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
