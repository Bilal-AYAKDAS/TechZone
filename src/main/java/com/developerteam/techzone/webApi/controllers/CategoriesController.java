package com.developerteam.techzone.webApi.controllers;


import com.developerteam.techzone.business.abstracts.ICategoryService;
import com.developerteam.techzone.entities.concreates.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {

    private ICategoryService categoryService;

    @Autowired
    public CategoriesController(ICategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping("/getall")
    public List<Category> getAll(){
        return this.categoryService.getAll();
    }

    //Devamı  yazılacak
    //...

}
