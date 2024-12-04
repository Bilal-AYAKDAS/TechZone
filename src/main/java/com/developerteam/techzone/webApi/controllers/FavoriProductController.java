package com.developerteam.techzone.webApi.controllers;


import com.developerteam.techzone.business.abstracts.IFavoriProductService;
import com.developerteam.techzone.entities.concreates.FavoriProduct;
import com.developerteam.techzone.entities.concreates.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/favoriProducts")
public class FavoriProductController {
    private IFavoriProductService favoriProductService;

    @Autowired
    public FavoriProductController(IFavoriProductService favoriProductService) {
        this.favoriProductService = favoriProductService;
    }

    @GetMapping("/getall")
    public List<FavoriProduct> getAll() {
        return this.favoriProductService.getAll();
    }

    @GetMapping("/{id}")
    public FavoriProduct getById(@PathVariable int id) {
        return this.favoriProductService.getById(id);
    }

    @GetMapping("getByUser")
    public List<FavoriProduct> getByUser(@RequestBody User user) {
        return this.favoriProductService.getByUser(user);
    }

    @PostMapping("/add")
    public FavoriProduct add(@RequestBody FavoriProduct favoriProduct) {
        return this.favoriProductService.add(favoriProduct);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        this.favoriProductService.delete(id);
    }



}
