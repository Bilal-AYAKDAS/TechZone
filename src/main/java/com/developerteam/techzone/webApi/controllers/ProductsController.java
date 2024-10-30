package com.developerteam.techzone.webApi.controllers;


import com.developerteam.techzone.business.abstracts.IProductService;
import com.developerteam.techzone.entities.concreates.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")

public class ProductsController {
    private IProductService productService;

    @Autowired
    public ProductsController(IProductService productService){
        this.productService = productService;
    }

    @GetMapping("/getall")
    public List<Product> getAll(){
        return this.productService.getAll();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable int id){
        return this.productService.getById(id);
    }

    @GetMapping("/getByCategoryId/{categoryId}")
    public List<Product> getByCategoryId(@PathVariable int categoryId){
        return this.productService.getByCategoryId(categoryId);
    }

    @GetMapping("/getByBrandId/{brandId}")
    public List<Product> getByBrandId(@PathVariable int brandId){
        return this.productService.getByBrandId(brandId);
    }

    @GetMapping("/getByCategoryIdAndBrandId/{categoryId}/{brandId}")
    public List<Product> getByCategoryIdAndBrandId(@PathVariable int categoryId,@PathVariable int brandId){
        return this.productService.getByCategoryIdAndBrandId(categoryId,brandId);
    }

    @GetMapping("/getByProductName/{productName}")
    public List<Product> getByProductName(@PathVariable String productName){
        return this.productService.getByProductName(productName);
    }


    @PostMapping("/add")
    public Product add (@RequestBody Product product){
        return this.productService.add(product);
    }

    @PutMapping("/update/{id}")
    public Product update(@PathVariable int id,@RequestBody Product product){
        return this.productService.update(id,product);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id){
        this.productService.delete(id);
    }




}
