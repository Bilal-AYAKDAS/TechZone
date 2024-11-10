package com.developerteam.techzone.business.abstracts;

import com.developerteam.techzone.entities.concreates.UserAdress;

import java.util.List;

public interface IUserAdressService {
    List <UserAdress> getAll();
    UserAdress getById(int id);
    List <UserAdress> getByUserId(int userId);
    UserAdress add(UserAdress userAdress);
    UserAdress update(int id,UserAdress userAdress);
    void delete(int id);

}
