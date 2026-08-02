package com.example.Security.controllers;

import com.example.Security.dto.LoginDto;
import com.example.Security.dto.LoginResponseDto;
import com.example.Security.dto.SignUpDto;
import com.example.Security.dto.UserDto;
import com.example.Security.services.AuthService;
import com.example.Security.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;
    
    @Value("${deploy.env}")
    private String deplotenv;


    // for signup Mapping
    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpDto signUpDto) {

        System.out.println("Signup API Hit");

        UserDto userDto = userService.signUp(signUpDto);

        return ResponseEntity.ok(userDto);
    }

    // for login mapping but when only send the one jwt request in the code
//    @PostMapping("/login")
//    private ResponseEntity<String> loginUser(@RequestBody LoginDto loginDto, HttpServletRequest request, HttpServletResponse response)
//    {
//        System.out.println("Login API Hit");
//        String token = authService.login(loginDto);
//        // add the cookie
//        Cookie cookie = new Cookie("token", token);
//        cookie.setHttpOnly(true);
//        response.addCookie(cookie);
//
//
//        return ResponseEntity.ok(token);
//    }

    // this is used when 2 kind of token Used
    // firsr access token second referesh token
    @PostMapping("/login")
    private ResponseEntity<LoginResponseDto> loginUser(@RequestBody LoginDto loginDto, HttpServletRequest request, HttpServletResponse response)
    {
        System.out.println("Login API Hit");
        LoginResponseDto token = authService.login(loginDto);
        // add the cookie to store the token
        Cookie cookie = new Cookie("RefreshToken", token.getRequestToken());
        cookie.setHttpOnly(true);
        cookie.setSecure("production".equals(deplotenv));
        response.addCookie(cookie);


        return ResponseEntity.ok(token);
    }

    // now makin request for the referesh token
    @PostMapping("/refresh")
    private ResponseEntity<LoginResponseDto> refresh(HttpServletRequest request)
    {
        String refereshToken =  Arrays.stream(request.getCookies())
                .filter(cookie -> "RefreshToken"
                        .equals(cookie.getName()))
               .map(Cookie::getValue)
                .findFirst().orElseThrow(()-> new AuthenticationServiceException("Refereh token not present inside the cookie {}"));

        LoginResponseDto loginRequestDto = authService.refreshToken(refereshToken);
        return ResponseEntity.ok(loginRequestDto);

    }

}
