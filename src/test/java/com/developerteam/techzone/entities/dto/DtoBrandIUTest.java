package com.developerteam.techzone.entities.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DtoBrandTest {

    private final Validator validator;

    public DtoBrandTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }


    @Test
    void testNameTooShort() {
        // Arrange
        DtoBrand dtoBrand = new DtoBrand();
        dtoBrand.setName("A"); // Invalid name: too short

        // Act
        Set<ConstraintViolation<DtoBrand>> violations = validator.validate(dtoBrand);

        // Assert
        assertEquals(1, violations.size());

    }

    @Test
    void testNameTooLong() {
        // Arrange
        DtoBrand dtoBrand = new DtoBrand();
        dtoBrand.setName("VeryLongBrandName"); // Invalid name: too long

        // Act
        Set<ConstraintViolation<DtoBrand>> violations = validator.validate(dtoBrand);

        // Assert
        assertEquals(1, violations.size());
    }
}
