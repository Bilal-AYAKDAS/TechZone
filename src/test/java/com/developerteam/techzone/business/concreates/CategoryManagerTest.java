package com.developerteam.techzone.business.concreates;

import com.developerteam.techzone.dataAccess.abstracts.ICategoryRepository;
import com.developerteam.techzone.entities.concreates.Brand;
import com.developerteam.techzone.entities.concreates.Category;
import com.developerteam.techzone.entities.dto.DtoCategory;
import com.developerteam.techzone.entities.dto.DtoCategoryIU;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryManagerTest {

    @Autowired
    private CategoryManager categoryManager;

    @Autowired
    private ICategoryRepository categoryRepository;


    @Test
    void testGetAll() {
        List<DtoCategory> categories = categoryManager.getAll();
        assertNotNull(categories);
        assertEquals(2, categories.size());
        assertEquals(1, categories.get(0).getId());
        assertEquals("Telefon",categories.get(1).getName());
    }

    @Test
    void testGetById() {
        DtoCategory dtoCategory = categoryManager.getById(1);
        assertNotNull(dtoCategory);
        assertEquals("Bilgisayar",dtoCategory.getName());
        assertEquals(1,dtoCategory.getId());
    }

    @Test
    @Transactional
    @Rollback(false)
    void testAdd() {
        DtoCategoryIU dtoCategoryIU = new DtoCategoryIU();
        dtoCategoryIU.setName("Bilgisayar");

        assertNotNull(dtoCategoryIU.getName());
        DtoCategory result = categoryManager.add(dtoCategoryIU);

        assertNotNull(result,"Result should not be null.");
        assertEquals("Bilgisayar", result.getName());

        //Database control
        Category savedCategory = categoryRepository.getById(1);
        assertNotNull(savedCategory, "Saved brand should not be null.");
        assertEquals(result.getName(), savedCategory.getName());
    }

    @Test
    @Transactional
    @Rollback(false)
    void testUpdate() {
        DtoCategoryIU updatedCategory = new DtoCategoryIU();
        updatedCategory.setName("Telefon");

        DtoCategory updating = categoryManager.update(2,updatedCategory);

        assertNotNull(updating);
        assertEquals("Telefon", updating.getName());

        //Database control
        Category foundCategory = categoryRepository.getById(2);
        assertEquals(updatedCategory.getName(), foundCategory.getName());
    }

    @Test
    @Transactional
    @Rollback(false)
    void testDelete() {
        Category category = categoryRepository.getById(3);
        assertNotNull(category);
        categoryRepository.delete(category);
    }

    @Test
    void testFindCategoryOrThrow() {
        Category result = categoryManager.findCategoryOrThrow(1);

        assertNotNull(result);
        assertEquals(1, result.getId(), "The category ID should match.");
        assertEquals("Bilgisayar", result.getName());
    }
}

