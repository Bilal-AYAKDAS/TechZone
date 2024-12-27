package com.developerteam.techzone.business.concreates;

import com.developerteam.techzone.business.abstracts.IProductService;
import com.developerteam.techzone.dataAccess.abstracts.IProductRepository;
import com.developerteam.techzone.entities.concreates.Brand;
import com.developerteam.techzone.entities.concreates.Category;
import com.developerteam.techzone.entities.concreates.Product;
import com.developerteam.techzone.entities.dto.DtoProduct;
import com.developerteam.techzone.entities.dto.DtoProductIU;
import com.developerteam.techzone.exception.BaseException;
import com.developerteam.techzone.exception.ErrorMessage;
import com.developerteam.techzone.exception.MessageType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductManager implements IProductService {

    @Autowired
    private IProductRepository productRepository;

    @Override
    public DtoProduct getById(int id) {
        Product product = findProductOrThrow(id);
        DtoProduct dtoProduct = new DtoProduct();
        dtoProduct.setId(product.getId());
        dtoProduct.setName(product.getName());
        dtoProduct.setPrice(product.getPrice());
        dtoProduct.setStockAmount(product.getStockAmount());
        dtoProduct.setDescription(product.getDescription());
        dtoProduct.setCategoryId(product.getCategory().getId());
        dtoProduct.setBrandId(product.getBrand().getId());
        return dtoProduct;
    }

    @Override
    public List<DtoProduct> getAll() {
        List<DtoProduct> dtoProducts = new ArrayList<>();
        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            DtoProduct dtoProduct = new DtoProduct();
            dtoProduct.setId(product.getId());
            dtoProduct.setName(product.getName());
            dtoProduct.setPrice(product.getPrice());
            dtoProduct.setStockAmount(product.getStockAmount());
            dtoProduct.setDescription(product.getDescription());
            dtoProduct.setImageUrl(product.getImageUrl());
            dtoProduct.setCategoryId(product.getCategory().getId());
            dtoProduct.setBrandId(product.getBrand().getId());
            dtoProducts.add(dtoProduct);
        }
        return dtoProducts;
    }

    @Override
    public List<DtoProduct> getByCategoryId(int categoryId) {
        List<DtoProduct> dtoProducts = new ArrayList<>();
        List<Product> products = productRepository.findByCategoryId(categoryId);
        for (Product product : products) {
            DtoProduct dtoProduct = new DtoProduct();
            dtoProduct.setId(product.getId());
            dtoProduct.setName(product.getName());
            dtoProduct.setPrice(product.getPrice());
            dtoProduct.setStockAmount(product.getStockAmount());
            dtoProduct.setDescription(product.getDescription());
            dtoProduct.setCategoryId(product.getCategory().getId());
            dtoProduct.setBrandId(product.getBrand().getId());
            dtoProducts.add(dtoProduct);
        }
        return dtoProducts;
    }

    @Override
    public List<DtoProduct> getByBrandId(int brandId) {
        List<DtoProduct> dtoProducts = new ArrayList<>();
        List<Product> products = productRepository.findByBrandId(brandId);
        for (Product product : products) {
            DtoProduct dtoProduct = new DtoProduct();
            dtoProduct.setId(product.getId());
            dtoProduct.setName(product.getName());
            dtoProduct.setPrice(product.getPrice());
            dtoProduct.setStockAmount(product.getStockAmount());
            dtoProduct.setDescription(product.getDescription());
            dtoProduct.setCategoryId(product.getCategory().getId());
            dtoProduct.setBrandId(product.getBrand().getId());
            dtoProducts.add(dtoProduct);
        }
        return dtoProducts;
    }

    @Override
    public List<DtoProduct> getByCategoryIdAndBrandId(int categoryId, int brandId) {
        List<DtoProduct> dtoProducts = new ArrayList<>();
        List<Product> products = productRepository.findByCategoryIdAndBrandId(categoryId,brandId);
        for (Product product : products) {
            DtoProduct dtoProduct = new DtoProduct();
            dtoProduct.setId(product.getId());
            dtoProduct.setName(product.getName());
            dtoProduct.setPrice(product.getPrice());
            dtoProduct.setStockAmount(product.getStockAmount());
            dtoProduct.setDescription(product.getDescription());
            dtoProduct.setCategoryId(product.getCategory().getId());
            dtoProduct.setBrandId(product.getBrand().getId());
            dtoProducts.add(dtoProduct);
        }
        return dtoProducts;
    }

    @Override
    public List<DtoProduct> getByProductName(String productName) {
        List<DtoProduct> dtoProducts = new ArrayList<>();
        List<Product> products = productRepository.findByNameContainingIgnoreCase(productName);
        for (Product product : products) {
            DtoProduct dtoProduct = new DtoProduct();
            dtoProduct.setId(product.getId());
            dtoProduct.setName(product.getName());
            dtoProduct.setPrice(product.getPrice());
            dtoProduct.setStockAmount(product.getStockAmount());
            dtoProduct.setDescription(product.getDescription());
            dtoProduct.setCategoryId(product.getCategory().getId());
            dtoProduct.setBrandId(product.getBrand().getId());
            dtoProducts.add(dtoProduct);
        }
        return dtoProducts;
    }

    @Override
    public DtoProduct add(DtoProductIU dtoProductIU) {
        Product product = new Product();
        BeanUtils.copyProperties(dtoProductIU, product);
        product.setImageUrl("imageURL");
        Category productCategory = new Category();
        productCategory.setId(dtoProductIU.getCategoryId());
        product.setCategory(productCategory);
        Brand productBrand = new Brand();
        productBrand.setId(dtoProductIU.getBrandId());
        product.setBrand(productBrand);
        Product dbProduct = this.productRepository.save(product);
        DtoProduct response = new DtoProduct();
        BeanUtils.copyProperties(dbProduct, response);
        response.setBrandId(dbProduct.getBrand().getId());
        response.setCategoryId(dbProduct.getCategory().getId());
        return response;
    }

    @Override
    public DtoProduct update(int id, DtoProductIU dtoProductIU) {
        Product updateProduct = findProductOrThrow(id);
        updateProduct.setName(dtoProductIU.getName());
        updateProduct.setPrice(dtoProductIU.getPrice());
        updateProduct.setStockAmount(dtoProductIU.getStockAmount());
        updateProduct.setDescription(dtoProductIU.getDescription());
        updateProduct.setImageUrl("imageURL");
        Brand productBrand = new Brand();
        productBrand.setId(dtoProductIU.getBrandId());
        updateProduct.setBrand(productBrand);
        Category productCategory = new Category();
        productCategory.setId(dtoProductIU.getCategoryId());
        updateProduct.setCategory(productCategory);
        Product dbProduct = this.productRepository.save(updateProduct);
        DtoProduct response = new DtoProduct();
        BeanUtils.copyProperties(dbProduct, response);
        response.setBrandId(dbProduct.getBrand().getId());
        response.setCategoryId(dbProduct.getCategory().getId());
        return response;


    }

    @Override
    public void delete(int id) {
        findProductOrThrow(id);
        this.productRepository.deleteById(id);
    }

    public Product findProductOrThrow(int id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, Integer.toString(id))));
    }

}
