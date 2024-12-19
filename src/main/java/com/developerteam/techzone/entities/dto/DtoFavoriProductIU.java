package com.developerteam.techzone.entities.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoFavoriProductIU {

    @NotNull(message = "Product is is can not be null")
    private int productId;
}
