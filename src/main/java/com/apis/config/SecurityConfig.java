package com.apis.config;

import com.apis.service.SecurityService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private SecurityService securityService;
    private AuthSuccessHandler authSuccessHandler; //custom success url handler

    public SecurityConfig(SecurityService securityService, AuthSuccessHandler authSuccessHandler) {
        this.securityService = securityService;
        this.authSuccessHandler = authSuccessHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {



        http
                .authorizeRequests()
                .antMatchers("/user/**").hasAuthority("Admin")
                .antMatchers("/project/**").hasAuthority("Manager")
                .antMatchers("/task/employee/**").hasAuthority("Employee")
                .antMatchers("/task/**").hasAuthority("Manager")
                .antMatchers("/", "/login", "/fragments/**", "/assets/**", "/images/**")
                .permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
//                .defaultSuccessUrl("/welcome")
                //custom handler in case for success, depends on roles nav to diff url
                .successHandler(authSuccessHandler)
                .failureUrl("/login?error=true") //when fail display error message

                .and()
                .logout()
                //"/logout" has to be exact, naming convention for spring security
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login") //navigate to login page

                .and()
                .rememberMe()
                .tokenValiditySeconds(120) //timeout in sec, if not assigned, unlimited
                .key("cybertekSecurity") //set a key in cookie, name can be any
                .userDetailsService(securityService); //pass securityService obj created in Service
    }



}
