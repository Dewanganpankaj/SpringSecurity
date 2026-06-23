package com.example.Security.services;

import com.example.Security.exceptions.ResourceNotFoundException;
import com.example.Security.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    // if you implement the user details service in logic at that time
    // you have to add the user in the respository it self

    private final UserRepository userRepository;

    // services mein logic write hota hai na
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByemail(username)
                .orElseThrow(()-> new ResourceNotFoundException("Resource not found" + username + "Not Found"));
    }
}
