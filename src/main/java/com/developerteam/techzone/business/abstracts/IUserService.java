package com.developerteam.techzone.business.abstracts;

import com.developerteam.techzone.entities.concreates.User;

import java.util.List;


public interface IUserService {
    List<User> getAll();
    User getById(int id);
    User getByEmail(String email);
    User getByEmailAndPassword(String email,String password);
    User add(User user);
    User update(int id,User user);
    void delete(int id);

}
