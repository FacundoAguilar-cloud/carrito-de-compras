package com.demo.app.demo_msvc_app.security.jwt.config;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.demo.app.demo_msvc_app.security.user.ShopUserDetailsService;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class ShopConfig {
    private final ShopUserDetailsService userDetailsService;
    private final JwtAuth jwtAuth;
    //aca vamos a poner todas las URL que queremos proteger para que solo se pueda acceder con un permiso
    private static final List <String> SECURED_URLS = List.of("/api/cart-item/**","/api/cart/**" );

@Bean
public ModelMapper modelMapper2(){
    return new ModelMapper();
}
@Bean
public PasswordEncoder passwordEncoder (){
    return new BCryptPasswordEncoder();
}
@Bean
public AuthenticationTokenFilter authTokenFilter(){
    return new AuthenticationTokenFilter();
}

@Bean
public AuthenticationManager authManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager(); 
}

@Bean
public DaoAuthenticationProvider authProvider(){
    lombok.var authProvider = new DaoAuthenticationProvider();

    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder( passwordEncoder());
    return authProvider;
}
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
 http.csrf(AbstractHttpConfigurer::disable)
    .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuth))
    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    .authorizeHttpRequests(auth -> auth.requestMatchers(SECURED_URLS.toArray(String[]::new)).authenticated()
        .anyRequest().permitAll());

http.authenticationProvider(authProvider());
http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);
return http.build();
}


}
