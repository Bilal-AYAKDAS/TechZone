package com.developerteam.techzone.business.concreates;

import com.developerteam.techzone.business.abstracts.IAuthService;
import com.developerteam.techzone.business.abstracts.IRefreshTokenService;
import com.developerteam.techzone.dataAccess.abstracts.IUserRepository;
import com.developerteam.techzone.entities.concreates.RefreshToken;
import com.developerteam.techzone.entities.concreates.User;
import com.developerteam.techzone.entities.dto.DtoUserIU;
import com.developerteam.techzone.jwt.AuthRequest;
import com.developerteam.techzone.jwt.AuthResponse;
import com.developerteam.techzone.jwt.JwtService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthManager implements IAuthService {

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

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        try {
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
            authenticationProvider.authenticate(auth);
            Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
            String accessToken = jwtService.generateToken(optionalUser.get());
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(optionalUser.get());
            refreshTokenService.add(refreshToken);
            return new AuthResponse(accessToken,refreshToken.getRefreshToken());
        } catch (Exception e) {
            System.out.println("Kullanıcı adı yada şifreniz hatalı");
        }
        return null;
    }

    @Override
    public DtoUserIU register(DtoUserIU newUser) {
        DtoUserIU dto =new DtoUserIU();
        User user = new User();
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setAge(newUser.getAge());
        user.setEmail(newUser.getEmail());
        user.setPhoneNumber(newUser.getPhoneNumber());
        user.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        user.setUserType("CUSTOMER");
        User savedUser = userRepository.save(user);
        BeanUtils.copyProperties(savedUser,dto);
        return dto;
    }


    //Login olan kullanıcıyı optional veri türünde döner
    @Override
    public Optional <User> getAuthenticatedUser() {
        String userEmail = jwtService.getAuthenticatedUsername();
        Optional<User> optionalUser = userRepository.findByEmail(userEmail);
        return optionalUser;
    }

    @Override
    public String getUserType(){
        String userType = jwtService.getUserTypeFromToken();
        System.out.println(userType);
        return userType;

    }
}
