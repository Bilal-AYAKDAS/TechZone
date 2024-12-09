package com.developerteam.techzone.webApi.controllers;


import com.developerteam.techzone.business.abstracts.IBrandService;
import com.developerteam.techzone.entities.concreates.Brand;
import com.developerteam.techzone.entities.dto.DtoBrand;
import com.developerteam.techzone.entities.dto.DtoBrandIU;
import com.developerteam.techzone.jwt.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
public class BrandsController {

    @Autowired
    private IBrandService brandService;


    @GetMapping("/getall")
    public List<DtoBrand> getAll() {
        return this.brandService.getAll();

    }

    @GetMapping("/{id}")
    public DtoBrand getById(@PathVariable int id){
        return this.brandService.getById(id);
    }

    @PostMapping("/add")
    public DtoBrand add(@RequestBody @Valid DtoBrandIU dtoBrandIU){
        return this.brandService.add(dtoBrandIU);
    }

    @PutMapping("/update/{id}")
    public DtoBrand update(@PathVariable int id,@RequestBody @Valid DtoBrandIU dtoBrandIU){
        return this.brandService.update(id,dtoBrandIU);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id){
        this.brandService.delete(id);
    }

}
