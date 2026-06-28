package com.example.Security.controllers;

import com.example.Security.dto.LoginDto;
import com.example.Security.dto.SignUpDto;
import com.example.Security.dto.UserDto;
import com.example.Security.services.AuthService;
import com.example.Security.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    private ResponseEntity<String> loginUser(@RequestBody LoginDto loginDto, HttpServletRequest request, HttpServletResponse response)
    {
        System.out.println("Login API Hit");
        String token = authService.login(loginDto);
        // add the cookie
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);


        return ResponseEntity.ok(token);
    }

}
