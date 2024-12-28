package com.developerteam.techzone.business.concreates;

import com.developerteam.techzone.dataAccess.abstracts.IRefreshTokenRepository;
import com.developerteam.techzone.dataAccess.abstracts.IUserRepository;
import com.developerteam.techzone.entities.concreates.RefreshToken;
import com.developerteam.techzone.entities.concreates.User;
import com.developerteam.techzone.jwt.AuthResponse;
import com.developerteam.techzone.jwt.JwtService;
import com.developerteam.techzone.jwt.RefreshTokenRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
class RefreshTokenManagerTest {

    @Autowired
    private IRefreshTokenRepository refreshTokenRepository;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private RefreshTokenManager refreshTokenManager;

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private AuthManager authManager;
    /*@BeforeEach
    void setUp() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("alidemir@gmail.com", null, List.of()));
    }*/

    @Test
    @Transactional
    @Rollback(false)
    void testCreateRefreshToken() {
        User user = userRepository.getById(14);
        RefreshToken token = refreshTokenManager.createRefreshToken(user);
        assertNotNull(token);

        assertNotNull(token.getExpireDate());
        long expirationTime  = token.getExpireDate().getTime() - System.currentTimeMillis();
        assertTrue(expirationTime > 0 && expirationTime <= 1000*60*60*4);

        assertEquals(user, token.getUser());

        assertNotNull(token.getRefreshToken());
        assertTrue(UUID.fromString(token.getRefreshToken()) instanceof UUID);
    }

    @Test
    void testIsRefreshTokenExpired() {
        RefreshToken token = new RefreshToken();
        token.setRefreshToken("14400000"); //milisaniye cinsinden 3 ay
        Date expiredDate = new Date(System.currentTimeMillis() + Long.parseLong(token.getRefreshToken()));
        new Date().before(expiredDate);
    }

    @Test
    @Transactional
    @Rollback(false)
    void testAdd() {
        User user = userRepository.getById(14);
        RefreshToken token = new RefreshToken();
        token.setRefreshToken("14400000"); //milisaniye cinsinden 3 ay
        Date expireDate = new Date(System.currentTimeMillis() + Long.parseLong(token.getRefreshToken()));
        token.setExpireDate(expireDate);
        token.setUser(user);

        RefreshToken result = refreshTokenManager.add(token);
        assertEquals("14400000", result.getRefreshToken());
        assertEquals(expireDate, result.getExpireDate());


        RefreshToken savedToken = refreshTokenRepository.findById(1).orElse(null);
        assertNotNull(savedToken);
        assertEquals(result.getRefreshToken(), savedToken.getRefreshToken());
        assertEquals(result.getExpireDate(), savedToken.getExpireDate());
    }

    @Test
    @Transactional
    @Rollback(false)
    void testRefreshToken() {
        User user = userRepository.getById(14);
        RefreshToken token = new RefreshToken();
        token.setRefreshToken("valid-refresh-token");
        token.setExpireDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 4));
        token.setUser(user);
        refreshTokenRepository.save(token);

        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest("valid-refresh-token");

        AuthResponse response = refreshTokenManager.refreshToken(refreshTokenRequest);

        // Response kontrolü
        assertNotNull(response);
        assertNotNull(response.getAccessToken());
        assertNotNull(response.getRefreshToken());

        Optional<RefreshToken> foundToken = refreshTokenRepository.findByRefreshToken(response.getRefreshToken());
        assertTrue(foundToken.isPresent());
    }
    }
