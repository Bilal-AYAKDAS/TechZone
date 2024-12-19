package com.developerteam.techzone.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoProduct {

    int id;
    private String name;
    private double price;
    private int stockAmount;
    private String description;
    private String imageUrl;
    private int categoryId;
    private int brandId;

}
