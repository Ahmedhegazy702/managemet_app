package com.keytool.keytool.controllers;
import com.keytool.keytool.config.JwtUtil;
import com.keytool.keytool.entity.Login;
import com.keytool.keytool.entity.User;
import com.keytool.keytool.services.AuthenticationService;
import com.keytool.keytool.services.EmployeeService;
import com.keytool.keytool.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private  UserService userService;
    @Autowired
    private JwtUtil jwtUtil ;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User registerRequest) {
        try {
            userService.registerUser(registerRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(registerRequest);
        } catch (IllegalArgumentException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }

    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login loginRequest) {
        boolean success= userService.login(loginRequest.getEmail(),loginRequest.getPassword());
        if(success) {
            //  return ResponseEntity.ok("Login Successful!");
          String token=  jwtUtil.generateToken(loginRequest.getEmail());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("token", token);

            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }



    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }


}
