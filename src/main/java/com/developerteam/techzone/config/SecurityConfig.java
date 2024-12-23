package com.developerteam.techzone.config;

import com.developerteam.techzone.jwt.AuthEntryPoint;
import com.developerteam.techzone.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public static final String AUTHENTICATE = "/authenticate";
    public static final String REGISTER = "/register";
    public static final String REFRESH_TOKEN = "/refreshToken";
    public static final String PRODUCTS = "/api/products/**";
    public static final String BRANDS = "/api/brands/**";
    public static final String CATEGORY = "/api/categories/**";

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private AuthEntryPoint authEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf->csrf.disable())
                .authorizeHttpRequests(requests ->
                        requests
                                .requestMatchers("/static/*","/bootstrap/*","/fontawesome-6.6.0-css/*","/jquery.ui/*", "/css/*", "/js/*", "/img/*","/*.html","/*.css","/*.js").permitAll()
                                .requestMatchers(AUTHENTICATE, REGISTER,REFRESH_TOKEN,PRODUCTS,CATEGORY,BRANDS)
                                .permitAll()
                                .anyRequest()
                                .authenticated())
                .exceptionHandling(exception ->exception.authenticationEntryPoint(authEntryPoint))
                .sessionManagement(
                        session ->
                                session.sessionCreationPolicy
                                        (SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}