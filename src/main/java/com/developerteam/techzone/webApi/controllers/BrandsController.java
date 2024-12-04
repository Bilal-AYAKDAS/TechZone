package com.developerteam.techzone.webApi.controllers;


import com.developerteam.techzone.business.abstracts.IBrandService;
import com.developerteam.techzone.entities.concreates.Brand;
import com.developerteam.techzone.entities.dto.DtoBrand;
import jakarta.validation.Valid;
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
    public DtoBrand getById(@PathVariable int id){
        return this.brandService.getById(id);
    }

    @PostMapping("/add")
    public DtoBrand add(@RequestBody @Valid DtoBrand dtoBrand){
        return this.brandService.add(dtoBrand);
    }

    @PutMapping("/update/{id}")
    public DtoBrand update(@PathVariable int id,@RequestBody @Valid DtoBrand dtoBrand){
        return this.brandService.update(id,dtoBrand);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id){
        this.brandService.delete(id);
    }

}
