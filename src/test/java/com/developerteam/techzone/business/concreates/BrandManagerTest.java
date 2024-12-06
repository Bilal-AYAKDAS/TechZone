package com.developerteam.techzone.business.concreates;

import com.developerteam.techzone.dataAccess.abstracts.IBrandRepository;
import com.developerteam.techzone.entities.concreates.Brand;
import com.developerteam.techzone.entities.dto.DtoBrand;
import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Set;

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
        assertEquals(5, brands.size());
        assertEquals(1, brands.get(0).getId());
        assertEquals("Apple", brands.get(0).getName());
    }

    @Test
    void testGetById() {
        DtoBrand brand = brandManager.getById(1);
        assertEquals("Apple", brand.getName());

    }

    @Test
    @Transactional
    @Rollback(false)
    void testAdd() {
        DtoBrand dtoBrand = new DtoBrand();
        dtoBrand.setName("Xiaomi");

        // Act
        DtoBrand result = brandManager.add(dtoBrand);

        // Assert
        assertEquals(dtoBrand.getName(), result.getName());


        // Database control
        //brandManager.getById(7);
        Brand savedBrand = brandRepository.getById(7);
        assertEquals(dtoBrand.getName(), savedBrand.getName());
    }

    @Test
    @Transactional
    @Rollback(false)
    void testUpdate() {
        DtoBrand dtoBrand = new DtoBrand();
        dtoBrand.setName("Lenovo");

        DtoBrand result = brandManager.add(dtoBrand);

        DtoBrand updatedBrand = new DtoBrand();
        updatedBrand.setName("hp");


        DtoBrand updating = brandManager.update(4, updatedBrand);

        assertNotNull(updating);
        assertEquals("hp", updating.getName());

        //Database control
        Brand foundBrand = brandRepository.getById(4);
        assertEquals(updatedBrand.getName(), foundBrand.getName());
    }

    @Test
    @Transactional
    @Rollback(false)
    void testDelete() {
        Brand brand = brandRepository.findAll().get(2);
        brandRepository.delete(brand);
    }
}
