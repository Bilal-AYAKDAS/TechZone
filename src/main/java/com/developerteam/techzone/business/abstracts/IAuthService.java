package com.developerteam.techzone.business.abstracts;

import com.developerteam.techzone.entities.concreates.User;
import com.developerteam.techzone.entities.dto.DtoUserIU;
import com.developerteam.techzone.jwt.AuthRequest;
import com.developerteam.techzone.jwt.AuthResponse;

import java.util.Optional;

public interface IAuthService {

    DtoUserIU register(DtoUserIU newUser);

    AuthResponse authenticate(AuthRequest request);

    Optional<User> getAuthenticatedUser();

}
