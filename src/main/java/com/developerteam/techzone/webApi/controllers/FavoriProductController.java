package com.developerteam.techzone.webApi.controllers;


import com.developerteam.techzone.business.abstracts.IFavoriProductService;
import com.developerteam.techzone.entities.dto.DtoFavoriProduct;
import com.developerteam.techzone.entities.dto.DtoFavoriProductIU;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/favoriProducts")
public class FavoriProductController {

    @Autowired
    private IFavoriProductService favoriProductService;

    @GetMapping("/getall")
    public List<DtoFavoriProduct> getAll() {
        return favoriProductService.getAll();
    }

    @PostMapping("/add")
    public DtoFavoriProduct add(@RequestBody DtoFavoriProductIU dtoFavoriProductIU) {
        return favoriProductService.add(dtoFavoriProductIU);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) {
        favoriProductService.delete(id);
    }



}
