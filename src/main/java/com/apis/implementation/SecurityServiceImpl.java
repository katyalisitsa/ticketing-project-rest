package com.apis.implementation;

import com.apis.entity.User;
import com.apis.entity.common.UserPrincipal;
import com.apis.repository.UserRepository;
import com.apis.service.SecurityService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {
    private UserRepository userRepository;

    public SecurityServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(s);
        if(user==null){
            throw new UsernameNotFoundException("This user does not exists");
        }
        return new UserPrincipal(user); //return after conver user to UserDetails
    }
}
