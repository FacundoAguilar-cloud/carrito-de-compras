package com.demo.app.demo_msvc_app.security.jwt.config;


import java.security.Key;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import com.demo.app.demo_msvc_app.security.user.ShopUserDetails;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
@Component
public class JwtUtils {
@Value("${auth.token.jwtSecret}")
private String jwtSecret;
@Value("${auth.token.expirationInMils}")
private int expirationTime;


public String createToken(Authentication authentication){
   ShopUserDetails userPrincipal = (ShopUserDetails) authentication.getPrincipal();

   List <String> roles = userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

   return Jwts.builder()
   .setSubject(userPrincipal.getEmail())
   .claim("id", userPrincipal.getId()) //los claims basicamente son la info del usuario que va a estar codificada dentro del token
   .claim("roles", roles)
   .setIssuedAt(new Date())
   .setExpiration(new Date(new Date().getTime() + expirationTime))
   .signWith(key(), SignatureAlgorithm.HS256).compact();

}

private Key key(){
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
}

//ahora vamos a crear un metodo que va a cumplir la funcion de extrar el nombre de usuario directamente del token

public String getUsernameFromToken(String token){
    return Jwts.parserBuilder()
    .setSigningKey(key())
    .build()
    .parseClaimsJws(token)
    .getBody().getSubject();

}

//proximo a esto vamos a necesitar un método para validar el token

public boolean validateToken(String token){
try {
    Jwts.parserBuilder()
.setSigningKey(key())
.build()
.parseClaimsJws(token);

return true;
} catch (MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
   throw new JwtException(e.getMessage());
}

}





}
