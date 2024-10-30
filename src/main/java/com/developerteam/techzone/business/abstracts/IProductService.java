package com.developerteam.techzone.business.abstracts;

import com.developerteam.techzone.entities.concreates.Product;

import java.util.List;

public interface IProductService {

    List<Product> getAll();
    Product getById(int id);
    List<Product> getByCategoryId(int categoryId);
    List<Product> getByBrandId(int brandId);
    List<Product> getByCategoryIdAndBrandId(int categoryId,int brandId);
    List<Product> getByProductName(String productName);
    Product add(Product product);
    Product update(int id,Product product);
    void delete(int id);


}
