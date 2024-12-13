package com.developerteam.techzone.entities.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoProductIU {

    @Size(min = 2, max = 30, message = "Product name must be between 2 and 30 characters.")
    private String name;

    @NotNull(message = "Product price cannot be empty")
    private double price;

    @NotNull(message = "Product stock amount cannot be empty")
    private int stockAmount;

    @Size(max = 500,message = "Description  must be small  500 characters.")
    private String description;

    @NotNull(message = "Product category can not be empty.")
    private int categoryId;

    @NotNull(message = "Product brand can not be empty.")
    private int brandId;

}
