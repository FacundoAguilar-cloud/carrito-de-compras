package com.demo.app.demo_msvc_app.security.jwt.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.demo.app.demo_msvc_app.security.user.ShopUserDetailsService;

import lombok.RequiredArgsConstructor;
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class ShopConfig {
    private final ShopUserDetailsService userDetailsService;
    private final JwtAuth jwtAuth;

@Bean
public ModelMapper modelMapper(){
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

}
