package com.keytool.keytool.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.*;
import java.util.stream.Collectors;

public class GrantedAuthorityConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Override
   public Collection<GrantedAuthority> convert(Jwt jwt){
        Collection<GrantedAuthority> authorities = new ArrayList<>();


        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        if (realmAccess != null && realmAccess.containsKey("roles")) {
            @SuppressWarnings("unchecked")
            Collection<String> roles = (Collection<String>) realmAccess.get("roles");
            roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role)));
        }
        return authorities;
    }
}