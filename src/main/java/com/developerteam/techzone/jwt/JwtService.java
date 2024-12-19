package com.developerteam.techzone.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

    public static final String SECRET_KEY = "J4JB7uDLTWJlzLNr4bjKdI3lMqbuY0gkcNmNqD5fB7g=";

    public String generateToken(UserDetails  userDetails) {

        Map<String,Object> claimsMap = new HashMap<>();
        claimsMap.put("role", "ADMIN");

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .addClaims(claimsMap)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*2))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    public Key getKey() {
        byte [] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //User Role çekmek  için admin or normal user
    public  String getRoleFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("role", String.class);
    }

    public  Claims getClaimsFromToken(String token) {
        Claims claims =  Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token).getBody();
        return claims;
    }

    public <T> T exportToken(String token, Function<Claims, T> claimsFunction) {
        Claims claims =getClaimsFromToken(token);
        return claimsFunction.apply(claims);
    }

    public String getEmailByToken(String token) {
        return exportToken(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
       Date expiredDate = exportToken(token, Claims::getExpiration);
       return new Date().before(expiredDate);
    }

    public String getAuthenticatedUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
