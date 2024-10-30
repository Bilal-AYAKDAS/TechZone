package com.developerteam.techzone.webApi.controllers;


import com.developerteam.techzone.business.abstracts.ICategoryService;
import com.developerteam.techzone.entities.concreates.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public Category getById(@PathVariable int id){
        return this.categoryService.getById(id);
    }

    @PostMapping("/add")
    public Category add(@RequestBody Category category){
        return this.categoryService.add(category);
    }

    @PutMapping("/update/{id}")
    public Category update(@PathVariable int id,@RequestBody Category category){
        return this.categoryService.update(id,category);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id){
        this.categoryService.delete(id);
    }

}
