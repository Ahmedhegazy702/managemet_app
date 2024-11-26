package com.keytool.keytool.services;

import com.keytool.keytool.config.JwtUtil;
import com.keytool.keytool.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private final AuthenticationManager manager;
    @Autowired
    private final JwtUtil jwtUtil;

    public AuthenticationService(AuthenticationManager manager, JwtUtil jwtUtil) {
        this.manager = manager;
        this.jwtUtil = jwtUtil;
    }

    public String verify(User user){
        Authentication authentication=manager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword())
        );
        if(authentication.isAuthenticated()){
            return jwtUtil.generateToken(user.getEmail());
        }

       throw new RuntimeException("Authentication failed");


    }
}
