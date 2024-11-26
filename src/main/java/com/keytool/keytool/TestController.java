package com.keytool.keytool;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("all")
    public String getAll(){
        return "hello authenticated users";
    }

    @GetMapping("/users")
    @RolesAllowed("USER")
    public String getUser(){
        return "hello users";
    }

    @GetMapping("/admin")
    @RolesAllowed("ADMIN")
    public String getAdmin(){
        return "hello admin";
    }
}
