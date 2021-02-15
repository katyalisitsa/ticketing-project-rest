package com.apis.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface SecurityService extends UserDetailsService {
    //UserDetailsService from Spring Security
    @Override
    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;
}
