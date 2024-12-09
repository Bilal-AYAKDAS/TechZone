package com.developerteam.techzone.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoUserAdress {

    private String country;
    private String city;
    private String district;
    private String postCode;
    private String adress;
}
