package com.developerteam.techzone.business.concreates;


import com.developerteam.techzone.dataAccess.abstracts.IBrandRepository;
import com.developerteam.techzone.entities.concreates.Brand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import java.util.List;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BrandManagerTest {

    private BrandManager brandManager;

    @Autowired
    private IBrandRepository brandRepository;

    @BeforeEach
    void setUp() {
        brandManager = new BrandManager(brandRepository);
    }

    @Test
    void testGetAll() {
        List<Brand> brands = brandManager.getAll();
        assertEquals(1, brands.size());
        assertEquals(1, brands.get(0).getId());
        assertEquals("testAdd", brands.get(0).getName());

    }

    @Test
    void testGetById() {
        Brand brands = brandManager.getById(1);
        assertEquals("testAdd", brands.getName());
    }

    @Test
    @Transactional
    @Rollback(false)
    void testAdd() {
        Brand brands = new Brand();
        brands.setName("testAdd");
        Brand savedBrand = brandManager.add(brands);

        //Add name test
        assertEquals("testAdd", savedBrand.getName());

        //Add id test
        List<Brand> brand = brandManager.getAll();
        int s = brand.size();
        assertEquals(s, savedBrand.getId());

        //Register add name and id test
        Brand foundBrand = brandRepository.findById(savedBrand.getId()).orElse(null);
        assertNotNull(foundBrand);
        assertEquals(savedBrand.getId(), foundBrand.getId());
        assertEquals(savedBrand.getName(), foundBrand.getName());
    }

    @Test
    @Transactional
    @Rollback(false)
    void testUpdate() {
        int id = 2;
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new RuntimeException("Brand not found"));

        //brand is correct?
        assertEquals("forUpdate", brand.getName());

        Brand updatedBrand = new Brand();
        updatedBrand.setName("testUpdate");

        //Update
        Brand updating = brandManager.update(id, updatedBrand);

        assertNotNull(updating);
        assertEquals("testUpdate", updating.getName());

        //Register database?
        Brand foundBrand = brandRepository.findById(id).orElseThrow(() -> new RuntimeException("Brand not found after update"));
        assertEquals(updatedBrand.getName(), foundBrand.getName());

    }


    @Test
    @Transactional
    @Rollback(false)
    void testDelete() {
        Brand brand = brandRepository.findById(3).orElseThrow(() -> new RuntimeException("Brand not found"));
        brandRepository.delete(brand);

    }



}
