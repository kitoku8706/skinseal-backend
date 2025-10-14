package com.example.skin_back.user.config;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.skin_back.user.entity.UserEntity;

import lombok.Getter;

@Getter

public class CustomUserDetails implements UserDetails {

    private final UserEntity user; 

    public CustomUserDetails(UserEntity user) {
        this.user = user;
    }
    
    
    public UserEntity getUserEntity() {
        return user;
    }

    

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }
    
    public String getLoginId() {
        return user.getLoginId();
    }

    @Override
    public String getUsername() {
        return user.getLoginId(); 
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
        return true;
    }
}
