package com.developerteam.techzone.business.concreates;

import com.developerteam.techzone.business.abstracts.IAuthService;
import com.developerteam.techzone.business.abstracts.IUserAdressService;
import com.developerteam.techzone.dataAccess.abstracts.IUserAdressRepository;
import com.developerteam.techzone.dataAccess.abstracts.IUserRepository;
import com.developerteam.techzone.entities.concreates.User;
import com.developerteam.techzone.entities.concreates.UserAdress;
import com.developerteam.techzone.entities.dto.DtoUserAdress;
import com.developerteam.techzone.entities.dto.DtoUserAdressIU;
import com.developerteam.techzone.exception.BaseException;
import com.developerteam.techzone.exception.ErrorMessage;
import com.developerteam.techzone.exception.MessageType;
import com.developerteam.techzone.exception.UserNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserAdressManager  implements IUserAdressService {

    @Autowired
    private IUserAdressRepository userAdressRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IAuthService authService;

    //For Admin Admin için yetki kontrolü ekle
    @Override
    public List<UserAdress> getAll() {
        return userAdressRepository.findAll();
    }

    @Override
    public UserAdress getById(int id) {
        Optional <User> optionalUser = authService.getAuthenticatedUser();
        return userAdressRepository.findById(id).orElse(null);
    }

    @Override
    public List<UserAdress> getByUserId(int userId) {
        return userAdressRepository.findByUserId(userId);
    }


    /****
     *
     * **/


    //For Customer

    private boolean currentAdressHasUser(User user, UserAdress userAdress) {
        return user.getId()== userAdress.getUser().getId();
    }

    @Override
    public List<DtoUserAdress> getOwnAdressByUserId() {
        Optional <User> optionalUser = authService.getAuthenticatedUser();
        List<UserAdress> dbUserAdressList = userAdressRepository.findByUserId(optionalUser.get().getId());
        List<DtoUserAdress> dtoUserAdressList = new ArrayList<>();

        for (UserAdress userAdress : dbUserAdressList) {
            DtoUserAdress dtoUserAdress = new DtoUserAdress();
            BeanUtils.copyProperties(userAdress, dtoUserAdress);
            dtoUserAdressList.add(dtoUserAdress);
        }

        return dtoUserAdressList;
    }

    @Override
    public DtoUserAdress add(DtoUserAdressIU dtoUserAdressIU) {
        Optional<User> optionalUser = authService.getAuthenticatedUser();
        UserAdress userAdress = new UserAdress();
        BeanUtils.copyProperties(dtoUserAdressIU, userAdress);
        userAdress.setUser(optionalUser.get());
        UserAdress userAdressSaved = userAdressRepository.save(userAdress);
        DtoUserAdress response = new DtoUserAdress();
        BeanUtils.copyProperties(userAdressSaved, response);
        return response;
    }

    @Override
    public DtoUserAdress update(int id, DtoUserAdressIU dtoUserAdressIU) {
        Optional <UserAdress> existingUserAdress = userAdressRepository.findById(id);
        Optional<User> optionalUser = authService.getAuthenticatedUser();

        if(existingUserAdress.isEmpty()){
            new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, Integer.toString(id)));
        }

        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(
                    new ErrorMessage(MessageType.USER_NOT_FOUND, "User does not exist.")
            );
        }
        if (!currentAdressHasUser(optionalUser.get(), existingUserAdress.get())) {
            throw new UserNotFoundException(
                    new ErrorMessage(MessageType.USER_NOT_FOUND, "Adres has not this user.")
            );
        }

        UserAdress userAdress = new UserAdress();
        BeanUtils.copyProperties(dtoUserAdressIU, userAdress);
        userAdress.setUser(optionalUser.get());
        UserAdress userAdressSaved = userAdressRepository.save(userAdress);
        DtoUserAdress dtoUserAdress = new DtoUserAdress();
        BeanUtils.copyProperties(userAdressSaved, dtoUserAdress);
        return dtoUserAdress;

    }

    @Override
    public void delete(int id) {
        Optional<UserAdress> existingUserAdress = userAdressRepository.findById(id);
        if (existingUserAdress.isEmpty()) {
            return;
        }
        Optional<User> optionalUser = authService.getAuthenticatedUser();
        if (optionalUser.isEmpty()){
            return;
        }
        UserAdress userAdress = existingUserAdress.get();
        if (!currentAdressHasUser(optionalUser.get(), userAdress)) {
            return;
        }
        userAdressRepository.deleteById(id);

    }
}
