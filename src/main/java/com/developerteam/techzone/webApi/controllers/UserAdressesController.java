package com.developerteam.techzone.webApi.controllers;

import com.developerteam.techzone.business.abstracts.IUserAdressService;
import com.developerteam.techzone.entities.concreates.UserAdress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/useradresses")

public class UserAdressesController{
    private IUserAdressService userAdressesService;

    @Autowired
    public UserAdressesController(IUserAdressService userAdressService) {
        this.userAdressesService = userAdressService;
    }

    @GetMapping("/getall")
    public List<UserAdress> getAll(){
        return this.userAdressesService.getAll();
    }

    @GetMapping("/{id}")
    public UserAdress getById(@PathVariable int id){
        return this.userAdressesService.getById(id);
    }

    @GetMapping("/getByUserId/{userId}")
    public List<UserAdress> getByUserId(@PathVariable int userId){
        return this.userAdressesService.getByUserId(userId);
    }

    @PostMapping("/add")
    public UserAdress add (@RequestBody UserAdress userAdress){
        return this.userAdressesService.add(userAdress);
    }

    @PutMapping("/update/{id}")
    public UserAdress update(@PathVariable int id,@RequestBody UserAdress userAdress){
        return this.userAdressesService.update(id,userAdress);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id){
        this.userAdressesService.delete(id);
    }

}
