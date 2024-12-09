package com.developerteam.techzone.business.concreates;

import com.developerteam.techzone.business.abstracts.IRefreshTokenService;
import com.developerteam.techzone.dataAccess.abstracts.IRefreshTokenRepository;
import com.developerteam.techzone.entities.concreates.RefreshToken;
import com.developerteam.techzone.entities.concreates.User;
import com.developerteam.techzone.jwt.AuthResponse;
import com.developerteam.techzone.jwt.JwtService;
import com.developerteam.techzone.jwt.RefreshTokenRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenManager implements IRefreshTokenService {

    @Autowired
    private IRefreshTokenRepository refreshTokenRepository;

    @Autowired
    private JwtService jwtService;

    @Override
    public RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setExpireDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 4));
        refreshToken.setUser(user);
        return refreshToken;
    }

    @Override
    public boolean isRefreshTokenExpired(Date expiredDate) {
        return new Date().before(expiredDate);
    }

    @Override
    public RefreshToken add(RefreshToken refreshToken) {
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        Optional<RefreshToken> refreshTokenOptional =refreshTokenRepository.findByRefreshToken(refreshTokenRequest.getRefreshToken());
        if(refreshTokenOptional.isEmpty()){
            System.out.println("Refresh token not valid");
        }

        RefreshToken refreshToken = refreshTokenOptional.get();
        if (!isRefreshTokenExpired(refreshToken.getExpireDate())){
            System.out.println("Refresh token expired"+refreshTokenRequest.getRefreshToken());
        }

        String accessToken = jwtService.generateToken(refreshToken.getUser());
        RefreshToken savedRefreshToken =refreshTokenRepository.save(createRefreshToken(refreshToken.getUser()));

        return new AuthResponse(accessToken,savedRefreshToken.getRefreshToken());

    }
}
