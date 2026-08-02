package com.example.Security.config;

import com.example.Security.Filter.JwtAuthFilter;
import com.example.Security.entities.Enum.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.example.Security.entities.Enum.Role.ADMIN;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    // this all the public route can any one route in this space
    private static final String[] publicRoute = {"/auth/**",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    };


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(publicRoute).permitAll()
                        .requestMatchers("/posts/**").hasRole(ADMIN.name())
                        .anyRequest().authenticated())

                // add the filter as per our requirement
                .addFilterAfter(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        // permit all the request
//        .authorizeHttpRequests(auth -> auth
//                .anyRequest().permitAll());

        return http.build();
    }

    //Authentication manager is required
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }


}