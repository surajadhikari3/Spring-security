package com.example.security.Configuration.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
//Dummy UserDetailsService implementation
//Ideally we should load the user detail from the table with authorities (attached) to it or directory service like ldap
//To know all the roles belongs to a user check interface UserDetails interface
//
@Configuration
public class AppUserDetailsServiceImpl implements UserDetailsService {

    //jdbctemplate
    //user repostory

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return User.withUsername(username)
                .accountExpired(false)
                .accountLocked(false)
                .disabled(false)
                .password("Passw0rd123")
                .credentialsExpired(false)
                .roles(username.toUpperCase().contains("ADMIN") ? "ADMIN": "USER") // hack if username container admin we give user admin role otherwise user role, it should be coming from datastore
                .passwordEncoder(password -> new BCryptPasswordEncoder().encode(password))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}