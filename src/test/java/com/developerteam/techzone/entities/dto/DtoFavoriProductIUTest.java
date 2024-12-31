package com.developerteam.techzone.entities.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DtoProductIUTest {

    private final Validator validator;

    public DtoProductIUTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void testValidDtoProductIU() {
        DtoProductIU dto = new DtoProductIU();
        dto.setName("Valid Product");
        dto.setPrice(100.0);
        dto.setStockAmount(10);
        dto.setDescription("This is a valid description.");
        dto.setCategoryId(1);
        dto.setBrandId(1);


        Set<ConstraintViolation<DtoProductIU>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty(), "Valid DTO should not cause validation errors.");
    }

    @Test
    void testInvalidNameTooShort() {
        DtoProductIU dto = new DtoProductIU();
        dto.setName("A"); // Çok kısa
        dto.setPrice(100.0);
        dto.setStockAmount(10);
        dto.setDescription("This is a valid description.");
        dto.setCategoryId(1);
        dto.setBrandId(1);


        Set<ConstraintViolation<DtoProductIU>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty(), "Name too short should cause validation errors.");
        ConstraintViolation<DtoProductIU> violation = violations.iterator().next();
        assertEquals("Product name must be between 2 and 30 characters.", violation.getMessage());
    }

    @Test
    void testInvalidDescriptionTooLong() {
        DtoProductIU dto = new DtoProductIU();
        dto.setName("Valid Product");
        dto.setPrice(100.0);
        dto.setStockAmount(10);
        dto.setDescription("A".repeat(501)); // Çok uzun açıklama
        dto.setCategoryId(1);
        dto.setBrandId(1);

        Set<ConstraintViolation<DtoProductIU>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty(), "Description too long should cause validation errors.");
        ConstraintViolation<DtoProductIU> violation = violations.iterator().next();
        assertEquals("Description  must be small  500 characters.", violation.getMessage());
    }
}
