package com.developerteam.techzone.entities.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DtoUserAdressIUTest {
    private final Validator validator;

    public DtoUserAdressIUTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void testValidDtoUserAdressIU() {
        DtoUserAdressIU dto = new DtoUserAdressIU();
        dto.setCountry("Turkey");
        dto.setCity("Istanbul");
        dto.setDistrict("Kadikoy");
        dto.setPostCode("12345");
        dto.setAdress("Bagdat Street, No:123");

        Set<ConstraintViolation<DtoUserAdressIU>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty(), "Valid DTO should not cause validation errors.");
    }

    @Test
    void testInvalidPostCodePattern() {
        // Arrange: Geçersiz bir PostCode ile DTO oluştur
        DtoUserAdressIU dto = new DtoUserAdressIU();
        dto.setCountry("Turkey");
        dto.setCity("Istanbul");
        dto.setDistrict("Kadikoy");
        dto.setPostCode("abc123"); // Geçersiz
        dto.setAdress("Bagdat Street, No:123");

        Set<ConstraintViolation<DtoUserAdressIU>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty(), "Invalid PostCode should cause validation errors.");
        ConstraintViolation<DtoUserAdressIU> violation = violations.iterator().next();
        assertEquals("PostCode must be numeric and between 4 and 10 digits.", violation.getMessage());
    }

    @Test
    void testInvalidAddressTooShort() {
        // Arrange: Çok kısa bir adress ile DTO oluştur
        DtoUserAdressIU dto = new DtoUserAdressIU();
        dto.setCountry("Turkey");
        dto.setCity("Istanbul");
        dto.setDistrict("Kadikoy");
        dto.setPostCode("12345");
        dto.setAdress("123"); // Geçersiz

        Set<ConstraintViolation<DtoUserAdressIU>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty(), "Address too short should cause validation errors.");
        ConstraintViolation<DtoUserAdressIU> violation = violations.iterator().next();
        assertEquals("Address must be between 5 and 255 characters.", violation.getMessage());
    }

}
