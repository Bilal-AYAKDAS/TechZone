package com.developerteam.techzone.business.abstracts;

import com.developerteam.techzone.entities.concreates.User;
import com.developerteam.techzone.entities.dto.DtoUser;
import com.developerteam.techzone.entities.dto.DtoUserForAdmin;

import java.util.List;


public interface IUserService {
    List<DtoUserForAdmin> getAll();
    User getById(int id);
    User getByEmail(String email);
    User getByEmailAndPassword(String email,String password);
    User add(User user);
    User update(int id,User user);
    void delete(int id);

    //FOR CUSTOMER
    DtoUser getOwnInfo();
    DtoUser updateOwnInfo(DtoUser dtoUser);
}
