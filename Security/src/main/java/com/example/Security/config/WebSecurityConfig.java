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
import org.springframework.security.crypto.password4j.BcryptPassword4jPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
// while working with the web security at that time you have to
// EnableWebSecuity is mendatory
// HTTPSecurity

public class WebSecurityConfig {
    // this the websecurity
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception 
//    {
//        // implement some method
//        httpSecurity.authorizeHttpRequests(auth -> auth
//                .requestMatchers("/posts").permitAll()
//                .requestMatchers("/posts/**").hasAnyRole("Admin")
//                .formLogin(Customizer.withDefaults()) // 👈 IMPORTANT (enables login page)
//
//                .httpBasic(Customizer.withDefaults()); // optional (Postman testing)
//
//
//
//        return httpSecurity.build();
//    }
    {


        httpSecurity.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login").permitAll()     // 👈 allow login
                        .requestMatchers("/posts").permitAll()     // public
                        .requestMatchers("/posts/**").hasRole("Admin") // protected
                        .anyRequest().authenticated()              // rest secured
                )

                .formLogin(Customizer.withDefaults()) // 👈 IMPORTANT (enables login page)

                .httpBasic(Customizer.withDefaults()); // optional (Postman testing)

        return httpSecurity.build();
    }

    //this add the role of the perticular user
    @Bean
    UserDetailsService MyInmemeoryUserDetailsServices()
    {
        UserDetails adminuser =  User.withUsername("Pankaj")
                .password(passwordEncoder().encode("Pankaj"))
                .roles("Admin")
                .build();


        UserDetails teammate =  User.withUsername("Aman")
                .password(passwordEncoder().encode("AmanSa"))
                .roles("User")
                .build();


        // this is inbuild function that we have for user role
        return new InMemoryUserDetailsManager(adminuser,teammate);
    }

    @Bean
    PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }




}
