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

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    public static final String AUTHENTICATE = "/authenticate";
    public static final String REGISTER = "/register";
    public static final String CHANGE_PASSWD = "/changePassword";
    public static final String REFRESH_TOKEN = "/refreshToken";
    public static final String CHANGE_PASSWD_API = "/changePassword";
    public static final String PRODUCTS = "/api/products/**";
    public static final String BRANDS = "/api/brands/**";
    public static final String USERS = "/api/users/**";
    public static final String ORDERS = "/api/orders/**";
    public static final String CATEGORY = "/api/categories/**";

    public static final String ADMIN_PAGE = "/admin/**";
    public static final String ADMIN_SOURCE = "/html/admin/**";
    public static final String CUSTOMER = "/customer/**";
    public static final String CUSTOMER_SOURCE = "/html/customer/**";

    private static final List<String> PUBLIC_URLS = List.of(
            "/css/**",
            "/js/**",
            "/jquery.ui/**",
            "/fontawesome-6.6.0-css/**",
            "/images/**",
            "/bootstrap/**",
            "/img/**",
            "/index",
            "/html/public/**",
            "/public/**",
            "/",
            "/uploads/**",
            ORDERS,
            USERS,
            AUTHENTICATE,
            REGISTER,
            REFRESH_TOKEN,
            CHANGE_PASSWD_API,
            CHANGE_PASSWD,
            PRODUCTS,
            CATEGORY,
            BRANDS,
            ADMIN_PAGE,
            ADMIN_SOURCE,
            CUSTOMER,
            CUSTOMER_SOURCE
    );


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
                        // Genel erişime açık URL'ler
                        .requestMatchers(PUBLIC_URLS.toArray(new String[0])).permitAll()
                        // Sadece ADMIN kullanıcılarına açık olan URL
                        .requestMatchers(ADMIN_PAGE,ADMIN_SOURCE).hasAuthority("ADMIN")
                        .requestMatchers(CUSTOMER,CUSTOMER_SOURCE).hasAuthority("CUSTOMER")
                        // Diğer tüm istekler için giriş gereklidir
                        .anyRequest().authenticated()
                )
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