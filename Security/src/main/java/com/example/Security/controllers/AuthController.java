//package com.example.Security.controllers;
//
//import com.example.Security.dto.SignUpDto;
//import com.example.Security.dto.UserDto;
//import com.example.Security.services.UserService;
//import lombok.RequiredArgsConstructor;
//
//import org.springframework.http.ResponseEntity;
//
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/auth")
//@RequiredArgsConstructor
//
//public class AuthController {
//    // here we can make a two request 1)first is for the signup 2)second is for the login
//    private final UserService userService;
//
//    @PostMapping("/SignUp")
//    private ResponseEntity<UserDto> SignUp(@RequestBody SignUpDto signUpDto)
//    {
//        // for the authentication we use the auth service but here we can directly use the user serivce
//        // now
//        UserDto userDto = userService.signUp(signUpDto);
//        return ResponseEntity.ok(userDto);
//    }
//
//
//}
package com.example.Security.controllers;

import com.example.Security.dto.SignUpDto;
import com.example.Security.dto.UserDto;
import com.example.Security.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpDto signUpDto) {

        System.out.println("Signup API Hit");

        UserDto userDto = userService.signUp(signUpDto);

        return ResponseEntity.ok(userDto);
    }
}
