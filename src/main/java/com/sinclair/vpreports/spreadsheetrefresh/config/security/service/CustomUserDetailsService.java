package com.sinclair.vpreports.spreadsheetrefresh.config.security.service;

import com.sinclair.vpreports.spreadsheetrefresh.config.security.model.UserPrivileges;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    public UserPrivileges loadUserByUsername(String username) throws UsernameNotFoundException {
        //TODO retrieve user from database

        UserPrivileges user = new UserPrivileges();
        user.setAuthenticated(true);
        user.setUsername(username);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setAuthenticated(true);
        user.setEnabled(true);
        final List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        String role = "ROLE_USER";
        grantedAuthorities.add(new SimpleGrantedAuthority(role));
        user.setAuthorities(grantedAuthorities);
        return user;
    }

}
