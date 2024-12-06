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

    private final Validator validator;

    public BrandManagerTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

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
    void testAddInvalidBrandEmptyName() {
        // Arrange
        DtoBrand dtoBrand = new DtoBrand();
        dtoBrand.setName("");

        Set<ConstraintViolation<DtoBrand>> violations = validator.validate(dtoBrand);
        assertEquals(2, violations.size(), "There should be 2 validation error for an empty name.");

        boolean hasNotEmptyMessage = violations.stream()
                .anyMatch(v -> v.getMessage().equals("Brand name cannot be empty."));

        assertEquals(true, hasNotEmptyMessage);
        
        assertThrows(ConstraintViolationException.class, () -> {
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }
            brandManager.add(dtoBrand);
        });

    }
    
    @Test
    void testAddInvalidBrandTooShortName() {
        // Arrange
        DtoBrand dtoBrand = new DtoBrand();
        dtoBrand.setName("A");

        // Validation Control
        Set<ConstraintViolation<DtoBrand>> violations = validator.validate(dtoBrand);
        assertEquals(1, violations.size());
        assertEquals("Brand name must be between 2 and 14 characters.", violations.iterator().next().getMessage());

        // If validation is unsuccess, should not call add
        assertThrows(ConstraintViolationException.class, () -> {
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }
            brandManager.add(dtoBrand);
        });
    }

    @Test
    void testAddInvalidBrandTooLongName() {
        // Arrange
        DtoBrand dtoBrand = new DtoBrand();
        dtoBrand.setName("VeryLongBrandName"); // Geçersiz: Çok uzun isim

        // Validation control
        Set<ConstraintViolation<DtoBrand>> violations = validator.validate(dtoBrand);
        assertEquals(1, violations.size(), "There should be 1 validation error for a long name.");


        // If validation is unsuccess, should not call add
        assertThrows(ConstraintViolationException.class, () -> {
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }
            brandManager.add(dtoBrand);
        });
    }

    @Test
    @Transactional
    @Rollback(false)
    void testAdd() {
        DtoBrand dtoBrand = new DtoBrand();
        dtoBrand.setName("Xiaomi");

        Set<ConstraintViolation<DtoBrand>> violations = validator.validate(dtoBrand);
        assertEquals(0, violations.size());

        // Act
        DtoBrand result = brandManager.add(dtoBrand);

        // Assert
        assertEquals(dtoBrand.getName(), result.getName());

        int s = (int)brandRepository.count();

        // Database control
        Brand savedBrand = brandRepository.findAll().get(s-1);
        assertEquals(dtoBrand.getName(), savedBrand.getName());
    }

    @Test
    @Transactional
    @Rollback(false)
    void testUpdate() {
        DtoBrand dtoBrand = new DtoBrand();
        dtoBrand.setName("hp");

        DtoBrand result = brandManager.add(dtoBrand);

        DtoBrand updatedBrand = new DtoBrand();
        updatedBrand.setName("Lenovo");

        int s = (int)brandRepository.count();
        DtoBrand updating = brandManager.update(s, updatedBrand);

        assertNotNull(updating);
        assertEquals("Lenovo", updating.getName());

        //Database control
        Brand foundBrand = brandRepository.findAll().get(s-1);
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
