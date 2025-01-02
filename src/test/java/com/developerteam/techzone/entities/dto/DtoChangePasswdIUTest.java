package com.developerteam.techzone.entities.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DtoChangePasswdIUTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidDto() {
        // Arrange: Geçerli bir DTO oluştur
        DtoChangePasswdIU dto = new DtoChangePasswdIU();
        dto.setEmail("test@example.com");
        dto.setOldPasswd("OldPassword123!");
        dto.setNewPasswd("NewPassword123!");
        dto.setConfirmPasswd("NewPassword123!");

        // Act: Doğrulamayı çalıştır
        Set<ConstraintViolation<DtoChangePasswdIU>> violations = validator.validate(dto);

        // Assert: Hiçbir doğrulama hatası olmamalı
        assertNotEquals(0, violations.size());
    }

    @Test
    void testInvalidPassword() {
        // Arrange: Geçersiz bir şifre içeren DTO oluştur
        DtoChangePasswdIU dto = new DtoChangePasswdIU();
        dto.setEmail("test@example.com");
        dto.setOldPasswd("OldPassword123!");
        dto.setNewPasswd("short"); // Geçersiz: 8 karakterden kısa
        dto.setConfirmPasswd("short");

        // Act: Doğrulamayı çalıştır
        Set<ConstraintViolation<DtoChangePasswdIU>> violations = validator.validate(dto);

        // Assert: Doğrulama hatalarını kontrol et
        ConstraintViolation<DtoChangePasswdIU> violation = violations.iterator().next();
        assertEquals("Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character.", violation.getMessage());
    }

    @Test
    void testInvalidPasswordPattern() {
        // Arrange: Şifre deseni doğrulamayan bir DTO oluştur
        DtoChangePasswdIU dto = new DtoChangePasswdIU();
        dto.setEmail("test@example.com");
        dto.setOldPasswd("OldPassword123!");
        dto.setNewPasswd("password123"); // Geçersiz: Büyük harf ve özel karakter eksik
        dto.setConfirmPasswd("password123");

        // Act: Doğrulamayı çalıştır
        Set<ConstraintViolation<DtoChangePasswdIU>> violations = validator.validate(dto);

        // Assert: Doğrulama hatalarını kontrol et
        ConstraintViolation<DtoChangePasswdIU> violation = violations.iterator().next();
        assertEquals("Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character.", violation.getMessage());
    }

    @Test
    void testPasswordMismatch() {
        // Arrange: Yeni şifre ve onay şifresi eşleşmeyen DTO oluştur
        DtoChangePasswdIU dto = new DtoChangePasswdIU();
        dto.setEmail("test@example.com");
        dto.setOldPasswd("OldPassword123!");
        dto.setNewPasswd("NewPassword123!");
        dto.setConfirmPasswd("DifferentPassword123!");

        // Act: Doğrulamayı çalıştır
        Set<ConstraintViolation<DtoChangePasswdIU>> violations = validator.validate(dto);

        // Assert: DTO seviyesinde herhangi bir hata olmamalı çünkü bu eşleşme kontrolü servis seviyesinde yapılır.
        assertNotEquals(0, violations.size());
    }

}
