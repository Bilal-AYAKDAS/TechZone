package com.developerteam.techzone.business.concreates;

import com.developerteam.techzone.business.abstracts.IProductService;
import com.developerteam.techzone.dataAccess.abstracts.IProductRepository;
import com.developerteam.techzone.entities.concreates.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductManager implements IProductService {

    private IProductRepository productRepository;

    @Autowired
    public ProductManager(IProductRepository productRepository){
        this.productRepository =productRepository;
    }

    //İçleri Doldurulacak Metotlar ...


    @Override
    public List<Product> getAll() {
        return this.productRepository.findAll();
    }

    @Override
    public Product getById(int id) {
        return this.productRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> getByCategoryId(int categoryId) {
        return this.productRepository.findByCategoryId(categoryId);
    }

    @Override
    public List<Product> getByBrandId(int brandId) {
        return this.productRepository.findByBrandId(brandId);
    }

    @Override
    public List<Product> getByCategoryIdAndBrandId(int categoryId, int brandId) {
        return this.productRepository.findByCategoryIdAndBrandId(categoryId,brandId);
    }

    @Override
    public List<Product> getByProductName(String productName) {
        return this.productRepository.findByNameContainingIgnoreCase(productName);
    }


    @Override
    public Product add(Product product) {
        return this.productRepository.save(product);
    }

    @Override
    public Product update(int id, Product product) {
        Optional<Product> existingProduct = this.productRepository.findById(id);
        if(existingProduct.isPresent()){
            Product updateProduct = existingProduct.get();
            updateProduct.setName(product.getName());
            updateProduct.setBrand(product.getBrand());
            updateProduct.setCategory(product.getCategory());
            updateProduct.setPrice(product.getPrice());
            updateProduct.setStockAmount(product.getStockAmount());
            updateProduct.setDescription(product.getDescription());
            updateProduct.setImageUrl(product.getImageUrl());
            updateProduct.setBrand(product.getBrand());
            updateProduct.setCategory(product.getCategory());
            return this.productRepository.save(updateProduct);

        }
        return null;
    }

    @Override
    public void delete(int id) {
        this.productRepository.deleteById(id);
    }


}
