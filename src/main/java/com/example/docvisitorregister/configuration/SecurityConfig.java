package com.example.docvisitorregister.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .requestMatchers("/public/**").permitAll()  // Public endpoint
//                .anyRequest().authenticated();  // All other endpoints require authentication
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()  // Disable CSRF protection (be cautious when using in production)
                .authorizeRequests()
                .requestMatchers("/**").permitAll()  // Allow public endpoints
                .anyRequest().authenticated();  // Require authentication for all other requests
        return http.build();
    }
}
