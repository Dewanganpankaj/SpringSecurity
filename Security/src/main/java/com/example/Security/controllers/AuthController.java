package com.example.Security.controllers;

import com.example.Security.dto.LoginDto;
import com.example.Security.dto.SignUpDto;
import com.example.Security.dto.UserDto;
import com.example.Security.services.AuthService;
import com.example.Security.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.security.autoconfigure.SecurityProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;
    // for signup Mapping
    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpDto signUpDto) {

        System.out.println("Signup API Hit");

        UserDto userDto = userService.signUp(signUpDto);

        return ResponseEntity.ok(userDto);
    }

    // for login mapping
    @PostMapping("/login")
    private ResponseEntity<String> loginUser(@RequestBody LoginDto loginDto)
    {
        System.out.println("Login API Hit");
        String token = authService.login(loginDto);
        return ResponseEntity.ok(token);
    }

}
