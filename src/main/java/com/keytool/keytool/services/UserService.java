package com.keytool.keytool.services;

import com.keytool.keytool.entity.User;
import com.keytool.keytool.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Primary
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;




    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }


    public User registerUser(User registerRequest) {

        if (userRepository.findByEmail(registerRequest.getEmail()) != null) {
            throw new RuntimeException("Email is already Exist");
        }


        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));





       return userRepository.save(registerRequest);


    }


    public boolean login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new IllegalArgumentException("user not found"));

        return passwordEncoder.matches(password,user.getPassword());
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public String updateRole(String email, String newRole) {
        User user = userRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("user not found"));

        if (!newRole.equals("USER") && !newRole.equals("ADMIN")) {
            throw new RuntimeException("Role is wrong");
        }

        user.setRole(newRole);
        userRepository.save(user);

        return "User roles updated successfully";
    }

    public User viewProfile(String email) {
         return userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("user not found"));

    }





    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("User not found with email: " + email));



        return new org.springframework.security.core.userdetails.User
                (user.getEmail(), user.getPassword(), getAuthorities(user));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("User not found with email: " + email));
    }
}
