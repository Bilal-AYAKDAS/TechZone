package com.developerteam.techzone.webApi.controllers;


import com.developerteam.techzone.business.abstracts.IBrandService;
import com.developerteam.techzone.entities.concreates.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
public class BrandsController {
    private IBrandService brandService;

    @Autowired
    public BrandsController(IBrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/getall")
    public List<Brand> getAll() {
        return this.brandService.getAll();
    }

    @GetMapping("/{id}")
    public Brand getById(@PathVariable int id){
        return this.brandService.getById(id);
    }

    @PostMapping("/add")
    public Brand add(@RequestBody Brand brand){
        return this.brandService.add(brand);
    }

    @PutMapping("/update/{id}")
    public Brand update(@PathVariable int id,@RequestBody Brand brand){
        return this.brandService.update(id,brand);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id){
        this.brandService.delete(id);
    }

}
