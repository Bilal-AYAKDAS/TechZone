package com.developerteam.techzone.entities.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DtoCategoryTest {

    private final Validator validator;

    public DtoCategoryTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void testNameTooShort() {
        DtoCategory dtoCategory = new DtoCategory();
        dtoCategory.setName("A");

        Set<ConstraintViolation<DtoCategory>> violations = validator.validate(dtoCategory);

        assertEquals(1, violations.size());


    }

    @Test
    void testNameTooLong() {
        DtoCategory dtoCategory = new DtoCategory();
        dtoCategory.setName("veryLongCategoryName");

        Set<ConstraintViolation<DtoCategory>> violations = validator.validate(dtoCategory);

        assertEquals(1, violations.size());
    }

}
