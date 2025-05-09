package com.demo.app.demo_msvc_app.security.jwt.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.demo.app.demo_msvc_app.security.user.ShopUserDetailsService;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class AuthenticationTokenFilter extends OncePerRequestFilter {
@Autowired
private  JwtUtils jwtUtils;
@Autowired
private  ShopUserDetailsService userDetailsService;
@Override
protected void doFilterInternal(
  @NonNull  HttpServletRequest request, 
  @NonNull  HttpServletResponse response, 
  @NonNull  FilterChain filterChain)
        throws ServletException, IOException {
    String jwt = parseJwt(request);
    try {
        if (StringUtils.hasText(jwt) && jwtUtils.validateToken(jwt)) {
            String username = jwtUtils.getUsernameFromToken(jwt);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    
            Authentication auth = new UsernamePasswordAuthenticationToken(null, userDetails, userDetails.getAuthorities());
    
            SecurityContextHolder.getContext().setAuthentication(auth);
    
    
        }
    } catch (JwtException e) {
       response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

       response.getWriter().write("Invalid or expired token, please try again"); 

        return;
    } catch(Exception e){
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.getWriter().write(e.getMessage());
        return;
    }
    filterChain.doFilter(request, response);
    
   
}

//el primer metodo que vamos a hacer es para extrar directamente el token del header

private String parseJwt(HttpServletRequest request){
    String headerAuth= request.getHeader("Authorization");
    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer")) {
        return headerAuth.substring(7); //aca basicamente se remueve el bearer y se toma unicamente el token
    }
    return null;
}




}
