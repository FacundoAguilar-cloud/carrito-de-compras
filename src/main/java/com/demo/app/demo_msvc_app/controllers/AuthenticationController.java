package com.demo.app.demo_msvc_app.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.demo.app.demo_msvc_app.request.LoginRequest;
import com.demo.app.demo_msvc_app.response.ApiResponse;
import com.demo.app.demo_msvc_app.response.TokenResponse;
import com.demo.app.demo_msvc_app.security.jwt.config.JwtUtils;
import com.demo.app.demo_msvc_app.security.user.ShopUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {
 private final AuthenticationManager authManager;
 private final JwtUtils jwtUtils;
//con la anotacion valid vamos a validar el input para asegurarnos de la clase que le estamos pasando
 
@PostMapping("/login")
public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest request){
    try {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    String jwt = jwtUtils.createToken(authentication);

    ShopUserDetails userDetails = (ShopUserDetails) authentication.getPrincipal();

    TokenResponse tokenResponse = new TokenResponse(userDetails.getId(), jwt);

    return ResponseEntity.ok(new ApiResponse("Login Successfully!", tokenResponse));
    } catch (AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("Something went wrong, invalid email or password", e.getMessage()));
    }
    
    

    
 }
}
