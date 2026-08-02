package com.example.Security.services;

import com.example.Security.entities.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;

@Service
public class JwtService {
    // in the JWT token file you have to add the two filed

    // import the jwt secret key here
    @Value("${jwt.secret-key}")
    private String jwtSecretKey;

    // hash the secret kry
    private SecretKey getJwtSecretKey()
    {
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }


    // first create the JWT token
    // here we can see only one token is used
    public String generateAccessToken(UserEntity user)
    {
       return Jwts.builder()
                .subject(user.getId().toString())
                // this is just type of payload
                .claim("email",user.getEmail())
                .claim("roles", Set.of("ADMIN","USER"))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 *60*10)) // 10 minute
                .signWith(getJwtSecretKey())
               .compact();
    }
    // long term refresh token
    public String generateRefreshToken(UserEntity user)
    {
        return Jwts.builder()
                .subject(user.getId().toString())
                // this is just type of payload
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000L *60 * 60 * 24 * 30 * 6 )) // this is f or the 6 month
                .signWith(getJwtSecretKey())
                .compact();
    }

    // verify the JWT token
    public Long getUserIdFromToken(String token)
    {
        Claims claims = Jwts.parser()
                .verifyWith(getJwtSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return Long.valueOf(claims.getSubject());
    }

}
