package com.developerteam.techzone.webApi.controllers;


import com.developerteam.techzone.business.abstracts.ICategoryService;
import com.developerteam.techzone.entities.concreates.Category;
import com.developerteam.techzone.entities.dto.DtoCategory;
import com.developerteam.techzone.entities.dto.DtoCategoryIU;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {

    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/getall")
    public List<DtoCategory> getAll(){
        return this.categoryService.getAll();
    }

    @GetMapping("/{id}")
    public DtoCategory getById(@PathVariable int id){
        return this.categoryService.getById(id);
    }

    @PostMapping("/add")
    public DtoCategory add(@RequestBody DtoCategoryIU dtoCategoryIU){
        return this.categoryService.add(dtoCategoryIU);
    }

    @PutMapping("/update/{id}")
    public DtoCategory update(@PathVariable int id,@RequestBody DtoCategoryIU dtoCategoryIU){
        return this.categoryService.update(id,dtoCategoryIU);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id){
        this.categoryService.delete(id);
    }

}
