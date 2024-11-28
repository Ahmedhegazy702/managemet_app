package com.keytool.keytool.services;

import com.keytool.keytool.entity.User;
import com.keytool.keytool.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    private final UserRepository userRepository;


    public EmployeeService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }


    public User viewProfile(String email) {
        return userRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("User not found with email:" + email));
    }




}
