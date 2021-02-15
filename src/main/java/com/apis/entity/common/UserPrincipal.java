package com.apis.entity.common;

import com.apis.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {
    // this class enables to convert User to Userdetails
    private User user;  //User entity
    public UserPrincipal(User user){
        this.user=user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList=new ArrayList<>();
        //describe authorization to Spring

        //ManyToOne relation
        // get all the roles and convert to authority
        GrantedAuthority authority=new SimpleGrantedAuthority(this.user.getRole().getDescription());
        authorityList.add(authority); //converted role add to the list

        //ManyToMany relations
//        this.user.getRoles().forEach(role -> {
//            GrantedAuthority authority=new SimpleGrantedAuthority(this.user.getRole().getDescription());
//            authorityList.add(authority);
//        });


        return authorityList;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.user.isEnabled();
    }

    public long getId(){
        return this.user.getId();
    }


}
