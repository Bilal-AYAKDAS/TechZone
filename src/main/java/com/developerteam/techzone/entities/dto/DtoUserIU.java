package com.developerteam.techzone.entities.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoUserIU {

    @Size(min = 2, max = 15, message = "FirstName must be between 2 and 15 characters.")
    private String firstName;

    @Size(min = 2, max = 15, message = "LastName must be between 2 and 15 characters.")
    private String lastName;

    @Min(value = 18, message = "Age must be at least 18.")
    private int age;

    @Email(message = "Email should be valid.")
    @NotBlank(message = "Email cannot be blank.")
    private String email;

    @Pattern(
            regexp = "^\\+?[0-9]{10,15}$",
            message = "PhoneNumber must be valid and contain only digits, optionally starting with a '+'."
    )
    private String phoneNumber;

    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters.")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$",
            message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character."
    )
    private String password;

}
