package com.developerteam.techzone.business.abstracts;

import com.developerteam.techzone.entities.concreates.UserAdress;
import com.developerteam.techzone.entities.dto.DtoUserAdress;
import com.developerteam.techzone.entities.dto.DtoUserAdressIU;

import java.util.List;

public interface IUserAdressService {

    //For Admin
    List <UserAdress> getAll();
    UserAdress getById(int id);
    List <UserAdress> getByUserId(int userId);

    //For customer
    List<DtoUserAdress> getOwnAdressByUserId();
    DtoUserAdress add(DtoUserAdressIU dtoUserAdressIU);
    DtoUserAdress update(int id,DtoUserAdressIU DtoUserAdressIU);
    void delete(int id);

}
