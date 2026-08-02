package com.example.Security.services;

import com.example.Security.dto.LoginDto;
import com.example.Security.dto.LoginResponseDto;
import com.example.Security.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;
    private final UserService userService;
    private final SessionService sessionService;

    public LoginResponseDto login(LoginDto loginDto) {

        Authentication authentication =
                authenticationManager.authenticate(

                        new UsernamePasswordAuthenticationToken(
                                loginDto.getEmail(),
                                loginDto.getPassword()
                        )
                );

        UserEntity user = (UserEntity) authentication.getPrincipal();

        String AccessToken =  jwtService.generateAccessToken(user);
        String RefreshToken =  jwtService.generateRefreshToken(user);
        // also meintain the session login
        sessionService.generateNewSession(user,RefreshToken);


        // neeed to return 2 thing make a dto for this
        return new LoginResponseDto(user.getId(),AccessToken,RefreshToken);
    }

    // this is the reuest when the access token is expire and use the request token to generate the access token
    public LoginResponseDto refreshToken(String refereshToken) {
        Long userId = jwtService.getUserIdFromToken(refereshToken);

        // check the refrech token is expired or not
        sessionService.validateSession(refereshToken);

        UserEntity user = userService.getUserID(userId);



        String AccessToken =  jwtService.generateAccessToken(user);
        return new LoginResponseDto(user.getId(),AccessToken,refereshToken);
    }
}