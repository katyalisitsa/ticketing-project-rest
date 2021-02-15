package com.apis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Configuration
public class AuthSuccessHandler implements AuthenticationSuccessHandler {
    //customize landing page after login page with valid credential



    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        //authorities coming from UserPrincipal, authority
        // determine each role's landing page below
        if (roles.contains("Admin")){
            httpServletResponse.sendRedirect("/user/create");
        }
        if (roles.contains("Manager")){
            httpServletResponse.sendRedirect("/task/create");
        }
        if (roles.contains("Employee")){
            httpServletResponse.sendRedirect("/task/employee");
        }

    }


}
