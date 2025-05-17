package com.demo.app.demo_msvc_app.security.jwt.config;




import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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

public class SecurityConfig {
    private final ShopUserDetailsService userDetailsService;
    private final JwtAuth jwtAuth;
    private final AuthenticationTokenFilter authenticationTokenFilter;
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
    .csrf(csrf -> csrf.disable())
    .httpBasic(Customizer.withDefaults())
    .authorizeHttpRequests(auth -> auth
    .requestMatchers("/api/cart/**").permitAll()
    //para logear
    .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
    .anyRequest().authenticated()
    )
    .sessionManagement(sess -> sess
    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    )
    .exceptionHandling(excep -> excep.authenticationEntryPoint(jwtAuth))
    .addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    
    return http.build();    
}

@Bean
public PasswordEncoder passwordEncoder (){
    return new BCryptPasswordEncoder();
}

@Bean
public AuthenticationManager authManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager(); 
}


}



