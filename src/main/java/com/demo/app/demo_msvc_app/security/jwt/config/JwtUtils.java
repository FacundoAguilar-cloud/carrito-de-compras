package com.demo.app.demo_msvc_app.security.jwt.config;


import java.util.List;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.demo.app.demo_msvc_app.security.user.ShopUserDetails;
@Component
public class JwtUtils {

private String jwtSecret;

private int expirationTime;


public String createToken(Authentication authentication){
   Algorithm algorithm = Algorithm.HMAC256(this.privateKey);


    List <String> roles = userPrincipal
    .getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

    return JWT.create()
        .withSubject(userPrincipal.getEmail())
        .withClaim("roles", roles)
        .withClaim("id", userPrincipal.getId())
        .withIssuedAt(new java.util.Date())
        .withExpiresAt(new java.util.Date(System.currentTimeMillis() + expirationTime))
        .sign(com.auth0.jwt.algorithms.Algorithm.HMAC256(jwtSecret));
}







}
