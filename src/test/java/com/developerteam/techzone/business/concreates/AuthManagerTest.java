package com.developerteam.techzone.business.concreates;

import com.developerteam.techzone.business.abstracts.IRefreshTokenService;
import com.developerteam.techzone.dataAccess.abstracts.IUserRepository;
import com.developerteam.techzone.entities.concreates.User;
import com.developerteam.techzone.entities.dto.DtoUserAdressIU;
import com.developerteam.techzone.entities.dto.DtoUserIU;
import com.developerteam.techzone.jwt.AuthRequest;
import com.developerteam.techzone.jwt.AuthResponse;
import com.developerteam.techzone.jwt.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
class AuthManagerTest {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private IRefreshTokenService refreshTokenService;

    @Autowired
    private UserManager userManager;

    @Autowired
    private AuthManager authManager;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("alidemir@gmail.com", null, List.of()));


    }

    @Test
    void testAuthenticate() {
        try {
            AuthRequest request = new AuthRequest();

            User savedUser = userRepository.findByEmail("alidemir@gmail.com").orElse(null);

            request.setEmail(savedUser.getEmail());
            request.setPassword("alidemir");

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
            authenticationProvider.authenticate(auth);

            AuthResponse response = authManager.authenticate(request);

            assertNotNull(response);

        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    @Rollback(false)
    void testRegister() {
        DtoUserIU newUser = new DtoUserIU();
        newUser.setFirstName("Ali");
        newUser.setLastName("Demir");
        newUser.setEmail("alidemir@gmail.com");
        newUser.setPhoneNumber("1234567");
        String rawpassword = "alidemir";
        newUser.setPassword(bCryptPasswordEncoder.encode(rawpassword));


        DtoUserIU result = authManager.register(newUser);
        assertNotNull(result);
        assertEquals("Ali", result.getFirstName());
        assertEquals("Demir", result.getLastName());
        assertEquals("alidemir@gmail.com", result.getEmail());
        assertEquals("1234567", result.getPhoneNumber());

        User savedUser = userRepository.findById(15).orElse(null);
        assertNotNull(savedUser);

        assertEquals(result.getLastName(), savedUser.getLastName());
        assertEquals(result.getEmail(), savedUser.getEmail());
        assertEquals(result.getPhoneNumber(), savedUser.getPhoneNumber());
        assertEquals(result.getPassword(), savedUser.getPassword());


    }


    @Test
    void testGetAuthenticatedUser() {
       String authenticatedEmail = jwtService.getAuthenticatedUsername();
       assertEquals("alidemir@gmail.com", authenticatedEmail);

        Optional<User> result = authManager.getAuthenticatedUser();
        assertTrue(result.isPresent());
        assertEquals("alidemir@gmail.com", result.get().getEmail());
        assertEquals(15, result.get().getId());


    }

    @Test
    void testGetUserType() {
        UserDetails  userDetails =org.springframework.security.core.userdetails.User.
                withUsername("alidemir@gmail.com")
                .password("alidemir")
                .roles("CUSTOMER")
                .build();

        String token = jwtService.generateToken(userDetails);

        assertNotNull(token);
    }
}
