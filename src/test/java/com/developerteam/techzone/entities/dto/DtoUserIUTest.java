package com.developerteam.techzone.entities.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DtoUserIUTest {

    private final Validator validator;

    public DtoUserIUTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void testValidDtoUserIU() {
        DtoUserIU dto = new DtoUserIU();
        dto.setFirstName("Bilal");
        dto.setLastName("Ayakdas");
        dto.setAge(25);
        dto.setEmail("bilalayakdas@gmail.com");
        dto.setPhoneNumber("3333333");
        dto.setPassword("bilalayakdas");

        Set<ConstraintViolation<DtoUserIU>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty(), "Valid DTO should not cause validation errors.");
    }

    @Test
    void testInvalidFirstNameTooShort() {
        DtoUserIU dto = new DtoUserIU();
        dto.setFirstName("B"); // Çok kısa
        dto.setLastName("Ayakdas");
        dto.setAge(25);
        dto.setEmail("bilalayakdas@gmail.com");
        dto.setPhoneNumber("+3333333");
        dto.setPassword("bilalayakdas");

        Set<ConstraintViolation<DtoUserIU>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty(), "FirstName too short should cause validation errors.");
        ConstraintViolation<DtoUserIU> violation = violations.iterator().next();
        assertEquals("FirstName must be between 2 and 15 characters.", violation.getMessage());
    }

    @Test
    void testInvalidAgeTooYoung() {
        // Arrange: Geçersiz bir DTO oluştur
        DtoUserIU dto = new DtoUserIU();
        dto.setFirstName("Bilal");
        dto.setLastName("Ayakdas");
        dto.setAge(10); // Çok genç
        dto.setEmail("bilalayakdas@gmail.com");
        dto.setPhoneNumber("+3333333");
        dto.setPassword("bilalayakdas");

        Set<ConstraintViolation<DtoUserIU>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty(), "Age too young should cause validation errors.");

    }

    @Test
    void testInvalidEmailBlank() {
        // Arrange: Boş bir email ile DTO oluştur
        DtoUserIU dto = new DtoUserIU();
        dto.setFirstName("Bilal");
        dto.setLastName("Ayakdas");
        dto.setAge(25);
        dto.setEmail(""); // Geçersiz
        dto.setPhoneNumber("+3333333");
        dto.setPassword("bilalayakdas");

        Set<ConstraintViolation<DtoUserIU>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty(), "Blank email should cause validation errors.");
        ConstraintViolation<DtoUserIU> violation = violations.iterator().next();
        assertEquals("Email cannot be blank.", violation.getMessage());
    }

    @Test
    void testInvalidPhoneNumberFormat() {
        // Arrange: Geçersiz bir telefon numarası ile DTO oluştur
        DtoUserIU dto = new DtoUserIU();
        dto.setFirstName("Bilal");
        dto.setLastName("Ayakdas");
        dto.setAge(25);
        dto.setEmail("bilalayakdas@gmail.com");
        dto.setPhoneNumber("12345abc"); // Geçersiz
        dto.setPassword("bilalayakdas");

        Set<ConstraintViolation<DtoUserIU>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty(), "Invalid phone number should cause validation errors.");
    }

    @Test
    void testInvalidPasswordMissingSpecialCharacter() {
        // Arrange: Geçersiz bir şifre ile DTO oluştur
        DtoUserIU dto = new DtoUserIU();
        dto.setFirstName("Bilal");
        dto.setLastName("Ayakdas");
        dto.setAge(25);
        dto.setEmail("bilalayakdas@gmail.com");
        dto.setPhoneNumber("+3333333");
        dto.setPassword("Password123"); // Özel karakter yok

        // Act: Validasyonu çalıştır
        Set<ConstraintViolation<DtoUserIU>> violations = validator.validate(dto);

        // Assert: Validasyon hatası olmalı
        assertFalse(violations.isEmpty(), "Password missing special character should cause validation errors.");

    }
}
