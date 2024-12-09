package com.developerteam.techzone.entities.dto;

import com.developerteam.techzone.entities.concreates.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoFavoriProduct {

    private int id;
    private Product product;

}
