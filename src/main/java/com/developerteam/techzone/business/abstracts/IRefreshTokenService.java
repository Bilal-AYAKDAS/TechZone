package com.developerteam.techzone.business.abstracts;

import com.developerteam.techzone.entities.concreates.RefreshToken;
import com.developerteam.techzone.entities.concreates.User;
import com.developerteam.techzone.jwt.AuthResponse;
import com.developerteam.techzone.jwt.RefreshTokenRequest;

import java.util.Date;

public interface IRefreshTokenService {

    RefreshToken createRefreshToken(User user) ;
    boolean isRefreshTokenExpired(Date expiredDate);
    AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
    RefreshToken add(RefreshToken refreshToken);

}
