package com.developerteam.techzone.business.concreates;

import com.developerteam.techzone.business.abstracts.IAuthService;
import com.developerteam.techzone.business.abstracts.IUserService;
import com.developerteam.techzone.dataAccess.abstracts.IUserRepository;
import com.developerteam.techzone.entities.concreates.User;
import com.developerteam.techzone.entities.dto.DtoUser;
import com.developerteam.techzone.exception.ErrorMessage;
import com.developerteam.techzone.exception.MessageType;
import com.developerteam.techzone.exception.UserNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserManager implements IUserService {


    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IAuthService authService;



    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }

    @Override
    public User getByEmailAndPassword(String email, String password) {
        return this.userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public User add(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(int id, User user) {
        Optional <User> existingUser = this.userRepository.findById(id);
        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();
            updatedUser.setEmail(user.getEmail());
            updatedUser.setPassword(user.getPassword());
            updatedUser.setFirstName(user.getFirstName());
            updatedUser.setLastName(user.getLastName());
            updatedUser.setPhoneNumber(user.getPhoneNumber());
            return this.userRepository.save(updatedUser);
        }
        return null;
    }

    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public DtoUser getOwnInfo() {
        Optional<User> optionalUser = authService.getAuthenticatedUser();
        if (optionalUser.isEmpty()){
            throw new UserNotFoundException(
                    new ErrorMessage(MessageType.USER_NOT_FOUND, "User does not exist.")
            );
        }
        DtoUser dtoUser = new DtoUser();
        BeanUtils.copyProperties(optionalUser.get(), dtoUser);
        return dtoUser;
    }

    @Override
    public DtoUser updateOwnInfo(DtoUser dtoUser) {
        Optional<User> optionalUser =authService.getAuthenticatedUser();

        if (optionalUser.isEmpty()){
            throw new UserNotFoundException(
                    new ErrorMessage(MessageType.USER_NOT_FOUND, "User does not exist.")
            );
        }
        User user = optionalUser.get();
        user.setFirstName(dtoUser.getFirstName());
        user.setLastName(dtoUser.getLastName());
        user.setPhoneNumber(dtoUser.getPhoneNumber());
        user.setEmail(dtoUser.getEmail());
        user.setAge(dtoUser.getAge());

        User dbUser = this.userRepository.save(user);
        DtoUser userResponse = new DtoUser();
        BeanUtils.copyProperties(dbUser, userResponse);
        return userResponse;
    }


}
