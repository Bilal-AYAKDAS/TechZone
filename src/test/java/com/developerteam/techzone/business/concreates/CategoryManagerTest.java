package com.developerteam.techzone.business.concreates;

import com.developerteam.techzone.dataAccess.abstracts.ICategoryRepository;
import com.developerteam.techzone.entities.concreates.Category;
import com.developerteam.techzone.entities.dto.DtoCategory;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryManagerTest {

    private CategoryManager categoryManager;

    @Autowired
    private ICategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        categoryManager = new CategoryManager(categoryRepository);
    }

    @Test
    void testGetAll() {
        List<Category> categories = categoryManager.getAll();
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
    }

    @Test
    @Transactional
    @Rollback(false)
    void testAdd() {
        DtoCategory dtoCategory = new DtoCategory();
        dtoCategory.setName("Bilgisayar");

        DtoCategory result = categoryManager.add(dtoCategory);

        assertEquals(dtoCategory.getName(), result.getName());

        //Database control
        Category savedCategory = categoryRepository.getById(1);
        assertEquals(dtoCategory.getName(), savedCategory.getName());
    }

    @Test
    @Transactional
    @Rollback(false)
    void testUpdate() {
        DtoCategory dtoCategory = new DtoCategory();
        dtoCategory.setName("Telefon");
        DtoCategory result = categoryManager.add(dtoCategory);

        DtoCategory updatedCategory = new DtoCategory();
        updatedCategory.setName("Tablet");

        DtoCategory updating = categoryManager.update(2,updatedCategory);

        assertNotNull(updating);
        assertEquals("Tablet", updating.getName());

        //Database control
        Category foundCategory = categoryRepository.getById(2);
        assertEquals(updatedCategory.getName(), foundCategory.getName());
    }

    @Test
    @Transactional
    @Rollback(false)
    void testDelete() {
        Category category = categoryRepository.getById(2);
        categoryRepository.delete(category);
    }
}
