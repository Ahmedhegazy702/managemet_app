package com.keytool.keytool.services;

import com.keytool.keytool.entity.User;
import com.keytool.keytool.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public EmployeeService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User viewProfile(String email) {
        return userRepository.findByEmail(email);
    }



    public User addEmployee(User employeeRequest) {
        if (userRepository.findByEmail(employeeRequest.getEmail()) != null) {
            throw new RuntimeException("email is already exist");
        }

        employeeRequest.setPassword(passwordEncoder.encode(employeeRequest.getPassword()));
       return userRepository.save(employeeRequest);

    }
}
