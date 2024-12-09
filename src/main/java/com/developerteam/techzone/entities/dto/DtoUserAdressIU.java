package com.developerteam.techzone.entities.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoUserAdressIU {


    @NotBlank(message = "Country cannot be blank.")
    @Size(min = 2, max = 50, message = "Country must be between 2 and 50 characters.")
    private String country;

    @NotBlank(message = "City cannot be blank.")
    @Size(min = 2, max = 50, message = "City must be between 2 and 50 characters.")
    private String city;

    @NotBlank(message = "District cannot be blank.")
    @Size(min = 2, max = 50, message = "District must be between 2 and 50 characters.")
    private String district;

    @NotBlank(message = "PostCode cannot be blank.")
    @Pattern(
            regexp = "^[0-9]{4,10}$",
            message = "PostCode must be numeric and between 4 and 10 digits."
    )
    private String postCode;

    @NotBlank(message = "Address cannot be blank.")
    @Size(min = 5, max = 255, message = "Address must be between 5 and 255 characters.")
    private String adress;
}
