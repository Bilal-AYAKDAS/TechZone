package com.developerteam.techzone.webApi.controllers;

import com.developerteam.techzone.business.abstracts.IAuthService;
import com.developerteam.techzone.business.abstracts.IRefreshTokenService;
import com.developerteam.techzone.entities.dto.DtoChangePasswdIU;
import com.developerteam.techzone.entities.dto.DtoUserIU;
import com.developerteam.techzone.jwt.AuthRequest;
import com.developerteam.techzone.jwt.AuthResponse;
import com.developerteam.techzone.jwt.RefreshTokenRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private IAuthService authService;

    @Autowired
    private IRefreshTokenService refreshTokenService;

    @PostMapping("/register")
    public DtoUserIU register(@Valid @RequestBody DtoUserIU newUser) {
        return authService.register(newUser);
    }

    @PostMapping("/authenticate")
    public AuthResponse authenticate(@Valid @RequestBody AuthRequest request) {
        return authService.authenticate(request);
    }

    @PostMapping("/refreshToken")
    public AuthResponse refreshToken(@RequestBody RefreshTokenRequest request) {
        return refreshTokenService.refreshToken(request);
    }
    @GetMapping("/getUserType")
    public String getUserType() {
        return authService.getUserType();
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestBody DtoChangePasswdIU changePasswd) {
        return authService.changePassword(changePasswd);
    }
}
