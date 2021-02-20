package com.apis.implementation;

import com.apis.dto.UserDTO;
import com.apis.entity.User;
import com.apis.util.MapperUtil;
import com.apis.service.SecurityService;
import com.apis.service.UserService;
import lombok.SneakyThrows;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class SecurityServiceImpl implements SecurityService {
    private UserService userService;
    private MapperUtil mapperUtil;

    public SecurityServiceImpl(UserService userService, MapperUtil mapperUtil) {
        this.userService = userService;
        this.mapperUtil = mapperUtil;
    }

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserDTO user = userService.findByUserName(s);
        if (user == null) {
            throw new UsernameNotFoundException("This user does not exists");
        }
        return new org.springframework.security.core.userdetails.User(user.getId().toString(), user.getPassword(), listAuthorities(user));
    }

    @Override
    public User loadUser(String s) throws AccessDeniedException {
        UserDTO user = userService.findByUserName(s);
        return mapperUtil.convert(user, new User());
    }

    private Collection<? extends GrantedAuthority> listAuthorities(UserDTO user) {
        List<GrantedAuthority> authorityList = new ArrayList<>();

        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getDescription());
        authorityList.add(authority);

        return authorityList;
    }
}
