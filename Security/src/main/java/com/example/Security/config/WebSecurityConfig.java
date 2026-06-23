package com.example.Security.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
    {
        //Bean is required while you try to implement the
        //implement the UserDetailsServiceRepo
        httpSecurity.authorizeHttpRequests(auth ->auth
                        .requestMatchers("/posts").permitAll()
                .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults());

        return httpSecurity.build();
    }

    // Another bean is also created for the user details services
    @Bean
    UserDetailsService myInMemoryUserDetailsServices()
    {
        // the User is present inside the spring security
        // single user
        UserDetails userDetails = User
                .withUsername("Panku")
                .password(passwordEncoder().encode("Pass"))
                .roles("User")
                .build();

        // same like multipple user
        // single user
        UserDetails Admin_user = User
                .withUsername("Panku")
                .password(passwordEncoder().encode("Pass"))
                .roles("User")
                .build();
    }


    @Bean
    PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }



}
