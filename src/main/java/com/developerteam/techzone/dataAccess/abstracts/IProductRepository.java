package com.developerteam.techzone.dataAccess.abstracts;

import com.developerteam.techzone.entities.concreates.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product,Integer> {

    //brandId ve categoryId'ye göre ürünleri getirir
    List<Product> findByBrandId(int brandId);

    List<Product> findByCategoryId(int categoryId);

    List<Product> findByCategoryIdAndBrandId(int categoryId,int brandId);

    List<Product> findByNameContainingIgnoreCase(String name);

}
