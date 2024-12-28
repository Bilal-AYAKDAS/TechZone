package com.developerteam.techzone.business.concreates;

import com.developerteam.techzone.dataAccess.abstracts.IBrandRepository;
import com.developerteam.techzone.dataAccess.abstracts.ICategoryRepository;
import com.developerteam.techzone.dataAccess.abstracts.IProductRepository;
import com.developerteam.techzone.entities.concreates.Brand;
import com.developerteam.techzone.entities.concreates.Category;
import com.developerteam.techzone.entities.concreates.Product;
import com.developerteam.techzone.entities.dto.DtoBrand;
import com.developerteam.techzone.entities.dto.DtoCategory;
import com.developerteam.techzone.entities.dto.DtoProduct;
import com.developerteam.techzone.entities.dto.DtoProductIU;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
class ProductManagerTest {

    @Autowired
    private ProductManager productManager;

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private BrandManager brandManager;

    @Autowired
    private CategoryManager categoryManager;


    @Test
    void testGetById() {
        DtoProduct dtoProduct = productManager.getById(1);
        assertNotNull(dtoProduct);
        assertEquals(1,dtoProduct.getId());
        assertEquals("-", dtoProduct.getDescription());
        assertEquals(null, dtoProduct.getImageUrl());
        assertEquals("A54", dtoProduct.getName());
        assertEquals(20000, dtoProduct.getPrice());
        assertEquals(5, dtoProduct.getStockAmount());
        assertEquals(5, dtoProduct.getBrandId());
        assertEquals(2, dtoProduct.getCategoryId());
    }

    @Test
    void testGetAll() {
        List<DtoProduct> dtoProducts = productManager.getAll();
        assertNotNull(dtoProducts);
        assertEquals(2, dtoProducts.size());
        assertEquals(1,dtoProducts.get(0).getId());
        assertEquals("-", dtoProducts.get(0).getDescription());
        assertEquals(null, dtoProducts.get(0).getImageUrl());
        assertEquals("A54", dtoProducts.get(0).getName());
        assertEquals(20000, dtoProducts.get(0).getPrice());
        assertEquals(5, dtoProducts.get(0).getStockAmount());
        assertEquals(5, dtoProducts.get(0).getBrandId());
        assertEquals(2, dtoProducts.get(0).getCategoryId());
    }

    @Test
    void testGetByCategoryId() {
        List<DtoProduct> dtoProducts = productManager.getByCategoryId(2);
        assertNotNull(dtoProducts);
        assertEquals(2,dtoProducts.get(1).getId());
        assertEquals("-", dtoProducts.get(1).getDescription());
        assertEquals(null, dtoProducts.get(1).getImageUrl());
        assertEquals("Iphone 11 pro", dtoProducts.get(1).getName());
        assertEquals(25000, dtoProducts.get(1).getPrice());
        assertEquals(2, dtoProducts.get(1).getStockAmount());
        assertEquals(1, dtoProducts.get(1).getBrandId());
        assertEquals(2, dtoProducts.get(1).getCategoryId());
    }

    @Test
    void testGetByBrandId() {
        List<DtoProduct> dtoProducts = productManager.getByBrandId(1);
        assertNotNull(dtoProducts);
        assertEquals(2,dtoProducts.get(0).getId());
        assertEquals("-", dtoProducts.get(0).getDescription());
        assertEquals("-", dtoProducts.get(0).getDescription());
        assertEquals(null, dtoProducts.get(0).getImageUrl());
        assertEquals("Iphone 11 pro", dtoProducts.get(0).getName());
        assertEquals(25000, dtoProducts.get(0).getPrice());
        assertEquals(2, dtoProducts.get(0).getStockAmount());
        assertEquals(2, dtoProducts.get(0).getCategoryId());
        assertEquals(1, dtoProducts.get(0).getBrandId());
    }

    @Test
    void testGetByCategoryIdAndBrandId() {
        List<DtoProduct> dtoProducts = productManager.getByCategoryIdAndBrandId(2,1);
        assertNotNull(dtoProducts);
        assertEquals(2,dtoProducts.get(0).getId());
        assertEquals("-", dtoProducts.get(0).getDescription());
        assertEquals("-", dtoProducts.get(0).getDescription());
        assertEquals(null, dtoProducts.get(0).getImageUrl());
        assertEquals("Iphone 11 pro", dtoProducts.get(0).getName());
        assertEquals(25000, dtoProducts.get(0).getPrice());
        assertEquals(2, dtoProducts.get(0).getStockAmount());
        assertEquals(2, dtoProducts.get(0).getCategoryId());
        assertEquals(1, dtoProducts.get(0).getBrandId());
    }

    @Test
    void testGetByProductName() {
        List<DtoProduct> dtoProducts = productManager.getByProductName("Iphone 11 pro");
        assertNotNull(dtoProducts);
        assertEquals(2,dtoProducts.get(0).getId());
        assertEquals("-", dtoProducts.get(0).getDescription());
        assertEquals("-", dtoProducts.get(0).getDescription());
        assertEquals(null, dtoProducts.get(0).getImageUrl());
        assertEquals("Iphone 11 pro", dtoProducts.get(0).getName());
        assertEquals(25000, dtoProducts.get(0).getPrice());
        assertEquals(2, dtoProducts.get(0).getStockAmount());
        assertEquals(2, dtoProducts.get(0).getCategoryId());
        assertEquals(1, dtoProducts.get(0).getBrandId());
    }

//    @Test
//    @Transactional
//    @Rollback(false)
//    void testAdd() {
//        DtoBrand dtoBrands = brandManager.getById(5);
//
//        DtoCategory dtoCategories = categoryManager.getById(2);
//
//        DtoProductIU dtoProductIU = new DtoProductIU();
//        dtoProductIU.setName("A54");
//        dtoProductIU.setDescription("-");
//        dtoProductIU.setPrice(20000);
//        dtoProductIU.setStockAmount(5);
//        dtoProductIU.setCategoryId(dtoCategories.getId());
//        dtoProductIU.setBrandId(dtoBrands.getId());
//
//        DtoProduct result = productManager.add(dtoProductIU);
//
//        assertNotNull(result);
//        assertEquals("A54", result.getName());
//        assertEquals(dtoCategories.getId(), result.getCategoryId());
//        assertEquals(dtoBrands.getId(), result.getBrandId());
//
//        Product savedProduct = productRepository.getById(1);
//        assertNotNull(savedProduct);
//        assertEquals(result.getName(), savedProduct.getName());
//        assertEquals("imageURL", savedProduct.getImageUrl());
//        assertEquals(result.getDescription(), savedProduct.getDescription());
//        assertEquals(result.getPrice(), savedProduct.getPrice());
//        assertEquals(result.getStockAmount(), savedProduct.getStockAmount());
//        assertEquals(dtoCategories.getId(), savedProduct.getCategory().getId());
//        assertEquals(dtoBrands.getId(), savedProduct.getBrand().getId());
//    }

//    @Test
//    @Transactional
//    @Rollback(false)
//    void testUpdate() {
//        DtoBrand dtoBrands = brandManager.getById(1);
//        DtoCategory dtoCategories = categoryManager.getById(2);
//
//        DtoProductIU dtoProductIU = new DtoProductIU();
//        dtoProductIU.setName("Iphone 11 pro");
//        dtoProductIU.setDescription("-");
//        dtoProductIU.setPrice(25000);
//        dtoProductIU.setStockAmount(2);
//        dtoProductIU.setCategoryId(dtoCategories.getId());
//        dtoProductIU.setBrandId(dtoBrands.getId());
//
//        DtoProduct result = productManager.update(2,dtoProductIU);
//        assertNotNull(result);
//        assertEquals("Iphone 11 pro", result.getName());
//        assertEquals(dtoCategories.getId(), result.getCategoryId());
//        assertEquals(dtoBrands.getId(), result.getBrandId());
//
//        Product savedProduct = productRepository.getById(2);
//        assertNotNull(savedProduct);
//        assertEquals(result.getName(), savedProduct.getName());
//        assertEquals("imageURL", savedProduct.getImageUrl());
//        assertEquals(result.getDescription(), savedProduct.getDescription());
//        assertEquals(result.getPrice(), savedProduct.getPrice());
//        assertEquals(result.getStockAmount(), savedProduct.getStockAmount());
//        assertEquals(dtoCategories.getId(), savedProduct.getCategory().getId());
//        assertEquals(dtoBrands.getId(), savedProduct.getBrand().getId());
//
//    }

    @Test
    @Transactional
    @Rollback(false)
    void testDelete() {
        Product product = productRepository.getById(3);
        assertNotNull(product);
        productRepository.delete(product);
    }

    @Test
    void testFindProductOrThrow() {
        Product result = productManager.findProductOrThrow(2);

        assertNotNull(result);
        assertEquals(2, result.getId(), "The category ID should match.");
        assertEquals("Iphone 11 pro", result.getName());
        assertEquals(25000, result.getPrice());
        assertEquals(2, result.getStockAmount());
        assertEquals(1, result.getBrand().getId());
        assertEquals(2, result.getCategory().getId());
    }
}
