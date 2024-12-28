package com.developerteam.techzone.webApi.controllers;


import com.developerteam.techzone.business.abstracts.IProductService;
import com.developerteam.techzone.entities.concreates.Product;
import com.developerteam.techzone.entities.dto.DtoProduct;
import com.developerteam.techzone.entities.dto.DtoProductIU;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/products")

public class ProductsController {

    @Autowired
    private IProductService productService;

    @GetMapping("/getall")
    public List<DtoProduct> getAll(){
        return this.productService.getAll();
    }

    @GetMapping("/{id}")
    public DtoProduct getById(@PathVariable int id){
        return this.productService.getById(id);
    }

    @GetMapping("/getByCategoryId/{categoryId}")
    public List<DtoProduct> getByCategoryId(@PathVariable int categoryId){
        return this.productService.getByCategoryId(categoryId);
    }

    @GetMapping("/getByBrandId/{brandId}")
    public List<DtoProduct> getByBrandId(@PathVariable int brandId){
        return this.productService.getByBrandId(brandId);
    }

    @GetMapping("/getByCategoryIdAndBrandId/{categoryId}/{brandId}")
    public List<DtoProduct> getByCategoryIdAndBrandId(@PathVariable int categoryId,@PathVariable int brandId){
        return this.productService.getByCategoryIdAndBrandId(categoryId,brandId);
    }

    @GetMapping("/getByProductName/{productName}")
    public List<DtoProduct> getByProductName(@PathVariable String productName){
        return this.productService.getByProductName(productName);
    }


    @PostMapping(value = "/add", consumes = {"multipart/form-data"})
    public DtoProduct add (@RequestPart("product") @Valid DtoProductIU dtoProductIU,
                           @RequestParam(value = "file",required = false) MultipartFile file){

        return this.productService.add(dtoProductIU,file);
    }

    @PutMapping(value = "/update/{id}", consumes = {"multipart/form-data"})
    public DtoProduct update(@PathVariable int id,
                             @RequestPart("product") @Valid DtoProductIU dtoProductIU,
                             @RequestParam(value="file",required = false) MultipartFile file
                             ){
        return this.productService.update(id,dtoProductIU,file);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id){
        this.productService.delete(id);
    }




}
