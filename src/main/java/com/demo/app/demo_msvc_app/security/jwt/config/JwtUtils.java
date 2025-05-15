package com.demo.app.demo_msvc_app.security.jwt.config;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.demo.app.demo_msvc_app.exceptions.JwtException;
import com.demo.app.demo_msvc_app.security.user.ShopUserDetails;


@Component
public class JwtUtils {
@Value("${auth.token.jwtSecret}")
private String jwtSecret;
@Value("${auth.token.expirationInMils}")
private int expirationTime;


public String createToken(Authentication authentication){
   ShopUserDetails userPrincipal = (ShopUserDetails) authentication.getPrincipal();

   List <String> roles = userPrincipal.getAuthorities().stream()
   .map(GrantedAuthority::getAuthority)
   .collect(Collectors.toList());

   return JWT.create()
   .withSubject(userPrincipal.getEmail())
   .withClaim("id", userPrincipal.getId()) //los claims basicamente son la info del usuario que va a estar codificada dentro del token
   .withArrayClaim("roles", roles.toArray(new String[0]))
   .withIssuedAt(new Date())
   .withExpiresAt(new Date(new Date().getTime() + expirationTime))
   .sign(Algorithm.HMAC256(jwtSecret));

}



//ahora vamos a crear un metodo que va a cumplir la funcion de extrar el nombre de usuario directamente del token

public String getUsernameFromToken(String token){
 DecodedJWT jwt = JWT.decode(token);
 return jwt.getSubject();   


}

//proximo a esto vamos a necesitar un método para validar el token

public boolean validateToken(String token){
try {
Algorithm algorithm = Algorithm.HMAC256(jwtSecret);

JWTVerifier jwtVerifier = JWT.require(algorithm).build();

jwtVerifier.verify(token);

return true;
} catch (JWTVerificationException e) {
    throw new JwtException("Invalid Token, please try again");
}

}

public Long getIdFromToken(String token){
    DecodedJWT jwt = JWT.decode(token);

    return jwt.getClaim("id").asLong();
}

public List <String> getRolesFromToken(String token){
    DecodedJWT jwt = JWT.decode(token);

    return jwt.getClaim("roles").asList(String.class);
}




}
