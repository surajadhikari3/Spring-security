package com.example.security.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //disabling the CSRF
        http.csrf(csrfConfigurer -> csrfConfigurer.disable())
                .cors(corsConfigurer -> corsConfigurer.disable()) // also disabling cors, we shouldn't diable cors
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests.requestMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll())
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests.requestMatchers(HttpMethod.GET, "/v3/api-docs/swagger-config").permitAll())
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests.requestMatchers(HttpMethod.GET, "/v3/api-docs/**").permitAll())
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests.requestMatchers(HttpMethod.GET, "/api/v1/customers").hasAuthority("ROLE_ADMIN"))
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults()) // http basic login is enabled
                .formLogin(Customizer.withDefaults()) // form login is enabled
        ;
        return http.build();
    }

//    @Bean
    public UserDetailsService users() {
        // The builder will ensure the passwords are encoded before saving in memory
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        UserDetails user = users
                .username("user")
                .password("Passw0rd123")
                .roles("USER") // user with USER ROLE
                .build();
        UserDetails admin = users
                .username("admin")
                .password("Passw0rd123")
                .roles("USER", "ADMIN") // admin user with USER and ADMIN role
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
}