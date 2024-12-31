package com.developerteam.techzone.entities.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DtoFavoriProductIUTest {

    private final Validator validator;

    public DtoFavoriProductIUTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void testValidProductId() {
        DtoFavoriProductIU dto = new DtoFavoriProductIU();
        dto.setProductId(1);

        Set<ConstraintViolation<DtoFavoriProductIU>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }


}
