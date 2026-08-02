package com.example.Security.Filter;
// this is a kind of filter that is used for the JWT filter
// now  we have to put this filter inside the websecurity config file

import com.example.Security.entities.UserEntity;
import com.example.Security.services.JwtService;
import com.example.Security.services.UserService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;
    @Autowired
    @Qualifier("handlerExceptionResolver")
     private HandlerExceptionResolver handlerExceptionResolver;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       try{
           final String requestTokenHeader = request.getHeader("Authentication");
           // is nahi hai tb ye krna hai na
           if(requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer"))
           {
               filterChain.doFilter(request,response);
           }
           // tokem look like "Bearer nirfefnefmf" but we want only token whose indexing is 1
           String token = requestTokenHeader.split("Bearer ")[1];

           Long userid = jwtService.getUserIdFromToken(token);
           if(userid != null  && SecurityContextHolder.getContext().getAuthentication() == null)
           {
               UserEntity user = userService.getUserID(userid);
               // piut the user inside the prinf security context holder
               UsernamePasswordAuthenticationToken authecationToken = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
               // this when you are handling with the network and DDOs realted attack
               authecationToken.setDetails(
                       new WebAuthenticationDetailsSource().buildDetails(request)
               );

               SecurityContextHolder.getContext().setAuthentication(authecationToken);
           }
           filterChain.doFilter(request,response);
       // one token have multiple header we only required the Authentication
       }
       catch (Exception ex) {
           handlerExceptionResolver.resolveException(
                   request,
                   response,
                   null,
                   ex
           );
       }






    }
}
