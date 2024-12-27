package com.developerteam.techzone.webApi.controllers;

import com.developerteam.techzone.business.abstracts.IUserService;
import com.developerteam.techzone.entities.concreates.User;
import com.developerteam.techzone.entities.dto.DtoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")

public class UsersController {
    private IUserService userService;

    @Autowired
    public UsersController(IUserService userService){
        this.userService = userService;
    }

    @GetMapping("/getall")
    public List<User> getAll(){
        return this.userService.getAll();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable int id){
        return this.userService.getById(id);
    }

    @GetMapping("/getByEmail/{email}")
    public User getByEmail(@PathVariable String email){
        return this.userService.getByEmail(email);
    }

    @GetMapping("/getByEmailAndPassword/{email}/{password}")
    public User getByEmailAndPassword(@PathVariable String email,@PathVariable String password){
        return this.userService.getByEmailAndPassword(email,password);
    }

    @PostMapping("/add")
    public User add (@RequestBody User user){
        return this.userService.add(user);
    }

    @PutMapping("/update/{id}")
    public User update(@PathVariable int id,@RequestBody User user){
        return this.userService.update(id,user);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id){
        this.userService.delete(id);
    }

    //for customer

    @GetMapping("/getOwnInfo")
    public DtoUser getOwnInfo(){
        return this.userService.getOwnInfo();
    }

    @PutMapping("/updateOwnInfo")
    public DtoUser updateOwnInfo(@RequestBody DtoUser dtoUser){
        return this.userService.updateOwnInfo(dtoUser);
    }

}
