package com.developerteam.techzone.business.concreates;

import com.developerteam.techzone.business.abstracts.IUserAdressService;
import com.developerteam.techzone.dataAccess.abstracts.IUserAdressRepository;
import com.developerteam.techzone.entities.concreates.UserAdress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserAdressManager  implements IUserAdressService {

    private IUserAdressRepository userAdressRepository;

    @Autowired
    public UserAdressManager(IUserAdressRepository userAdressRepository) {
        this.userAdressRepository = userAdressRepository;
    }


    @Override
    public List<UserAdress> getAll() {
        return userAdressRepository.findAll();
    }

    @Override
    public UserAdress getById(int id) {
        return userAdressRepository.findById(id).orElse(null);
    }

    @Override
    public List<UserAdress> getByUserId(int userId) {
        return userAdressRepository.findByUserId(userId);
    }

    @Override
    public UserAdress add(UserAdress userAdress) {
        return userAdressRepository.save(userAdress);
    }

    @Override
    public UserAdress update(int id, UserAdress userAdress) {
        Optional <UserAdress> existingUserAdress = userAdressRepository.findById(id);
        if (existingUserAdress.isPresent()){
            UserAdress updateUserAdress = existingUserAdress.get();
            updateUserAdress.setCountry(userAdress.getCountry());
            updateUserAdress.setCity(userAdress.getCity());
            updateUserAdress.setDistrict(userAdress.getDistrict());
            updateUserAdress.setPostCode(userAdress.getPostCode());
            updateUserAdress.setAdress(userAdress.getAdress());

            return userAdressRepository.save(updateUserAdress);
        }
        return null;
    }

    @Override
    public void delete(int id) {
        userAdressRepository.deleteById(id);

    }
}
