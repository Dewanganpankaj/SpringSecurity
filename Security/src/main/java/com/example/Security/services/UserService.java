package com.example.Security.services;

import com.example.Security.dto.LoginDto;
import com.example.Security.dto.SignUpDto;
import com.example.Security.dto.UserDto;
import com.example.Security.entities.UserEntity;
import com.example.Security.exceptions.ResourceNotFoundException;
import com.example.Security.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    // if you implement the user details service in logic at that time
    // you have to add the user in the respository it self

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    // services mein logic implement
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByemail(username)
                .orElseThrow(()-> new ResourceNotFoundException("Resource not found" + username + "Not Found"));
    }

    public UserDto signUp(SignUpDto signUpDto) {

        Optional<UserEntity> user =
                userRepository.findByemail(signUpDto.getEmail());

        if (user.isPresent()) {
            throw new RuntimeException(
                    "User already exists with email : "
                            + signUpDto.getEmail());
        }

        UserEntity toCreate =
                modelMapper.map(signUpDto, UserEntity.class);

        toCreate.setPassword(
                passwordEncoder.encode(signUpDto.getPassword())
        );

        UserEntity savedUser =
                userRepository.save(toCreate);

        return modelMapper.map(savedUser, UserDto.class);
    }



    // implement the signup function here
}
