package com.developerteam.techzone.webApi.controllers;

import com.developerteam.techzone.business.abstracts.IUserAdressService;
import com.developerteam.techzone.entities.concreates.UserAdress;
import com.developerteam.techzone.entities.dto.DtoUserAdress;
import com.developerteam.techzone.entities.dto.DtoUserAdressIU;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/useradresses")

public class UserAdressesController{

    @Autowired
    private IUserAdressService userAdressesService;

    //Admin için
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


    //Customer için

    @GetMapping("/ownAdress")
    public List<DtoUserAdress> getOwnAdress(){
        return userAdressesService.getOwnAdressByUserId();
    }

    @PostMapping("/add")
    public DtoUserAdress add (@RequestBody DtoUserAdressIU dtoUserAdressIU){
        return this.userAdressesService.add(dtoUserAdressIU);
    }

    @PutMapping("/update/{id}")
    public DtoUserAdress update(@PathVariable int id, @RequestBody DtoUserAdressIU dtoUserAdressIU){
        return this.userAdressesService.update(id,dtoUserAdressIU);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id){
        this.userAdressesService.delete(id);
    }

}
