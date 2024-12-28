package com.developerteam.techzone.business.concreates;

import com.developerteam.techzone.business.abstracts.IProductService;
import com.developerteam.techzone.dataAccess.abstracts.IProductRepository;
import com.developerteam.techzone.entities.concreates.Brand;
import com.developerteam.techzone.entities.concreates.Category;
import com.developerteam.techzone.entities.concreates.Product;
import com.developerteam.techzone.entities.dto.DtoProduct;
import com.developerteam.techzone.entities.dto.DtoProductIU;
import com.developerteam.techzone.entities.dto.FileStorageProperties;
import com.developerteam.techzone.exception.BaseException;
import com.developerteam.techzone.exception.ErrorMessage;
import com.developerteam.techzone.exception.MessageType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductManager implements IProductService {

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private FileStorageProperties fileStorageProperties;

    @Override
    public DtoProduct getById(int id) {
        Product product = findProductOrThrow(id);
        DtoProduct dtoProduct = new DtoProduct();
        dtoProduct.setId(product.getId());
        dtoProduct.setName(product.getName());
        dtoProduct.setPrice(product.getPrice());
        dtoProduct.setStockAmount(product.getStockAmount());
        dtoProduct.setImageUrl(product.getImageUrl());
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
    public DtoProduct add(DtoProductIU dtoProductIU, MultipartFile file) {

        Product product = new Product();
        BeanUtils.copyProperties(dtoProductIU, product);
        Category productCategory = new Category();
        productCategory.setId(dtoProductIU.getCategoryId());
        product.setCategory(productCategory);
        Brand productBrand = new Brand();
        productBrand.setId(dtoProductIU.getBrandId());
        product.setBrand(productBrand);
        Product dbProduct = this.productRepository.save(product);

        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String newFileName = dbProduct.getId() + extension;
        String uploadDir = fileStorageProperties.getUploadDir();
        Path filePath = Paths.get(uploadDir+newFileName);
        Product product1 =dbProduct;
        try {
            Files.createDirectories(filePath.getParent());
            Files.write(filePath,file.getBytes());
            product1 = this.productRepository.findById(dbProduct.getId()).get();
            product1.setImageUrl("http://localhost:8080/uploads/" + newFileName);
            this.productRepository.save(product1);
        }catch (Exception e){
            e.printStackTrace();
        }

        DtoProduct response = new DtoProduct();
        BeanUtils.copyProperties(product1, response);
        response.setBrandId(product1.getBrand().getId());
        response.setCategoryId(product1.getCategory().getId());
        return response;
    }

    @Override
    public DtoProduct update(int id, DtoProductIU dtoProductIU,MultipartFile file) {
        Product updateProduct = findProductOrThrow(id);
        updateProduct.setName(dtoProductIU.getName());
        updateProduct.setPrice(dtoProductIU.getPrice());
        updateProduct.setStockAmount(dtoProductIU.getStockAmount());
        updateProduct.setDescription(dtoProductIU.getDescription());
        Brand productBrand = new Brand();
        productBrand.setId(dtoProductIU.getBrandId());
        updateProduct.setBrand(productBrand);
        Category productCategory = new Category();
        productCategory.setId(dtoProductIU.getCategoryId());
        updateProduct.setCategory(productCategory);


        String originalFileName = file.getOriginalFilename();
        if (originalFileName != null) {
            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String newFileName = id + extension;
            String uploadDir = fileStorageProperties.getUploadDir();
            Path filePath = Paths.get(uploadDir+newFileName);
            try {
                Files.createDirectories(filePath.getParent());
                Files.write(filePath,file.getBytes());
                updateProduct.setImageUrl("http://localhost:8080/uploads/" + newFileName);
            }catch (Exception e){
                e.printStackTrace();
            }
        }


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
