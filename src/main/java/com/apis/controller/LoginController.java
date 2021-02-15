package com.apis.controller;

import com.apis.dto.UserDTO;
import com.apis.entity.ResponseWrapper;
import com.apis.entity.User;
import com.apis.entity.common.AuthenticationRequest;
import com.apis.exception.TicketingProjectException;
import com.apis.mapper.MapperUtil;
import com.apis.service.UserService;
import com.apis.util.JWTUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private AuthenticationManager authenticationManager;
    private UserService userService;
    private MapperUtil mapperUtil;
    private JWTUtil jwtUtil;

    public LoginController(AuthenticationManager authenticationManager, UserService userService, MapperUtil mapperUtil, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.mapperUtil = mapperUtil;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ResponseWrapper> doLogin(@RequestBody AuthenticationRequest authenticationRequest) throws TicketingProjectException {
        String password = authenticationRequest.getPassword();
        String username = authenticationRequest.getUsername();

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(authentication);

        UserDTO foundUser = userService.findByUserName(username);

        User convertedUser = mapperUtil.convert(foundUser, new User());

        if (!foundUser.isEnabled()) {
            throw new TicketingProjectException("Please verify your user");
        }

        String jwtToken = jwtUtil.generateToken(convertedUser);

        return ResponseEntity.ok(new ResponseWrapper("Login Successful", jwtToken));

    }

}
