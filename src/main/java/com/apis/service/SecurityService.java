package com.apis.service;

import com.apis.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.nio.file.AccessDeniedException;

public interface SecurityService extends UserDetailsService {
    //UserDetailsService from Spring Security
    @Override
    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;

    User loadUser(String s) throws AccessDeniedException;
}
