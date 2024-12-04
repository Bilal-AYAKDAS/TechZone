package com.developerteam.techzone.entities.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoBrand {

    @NotEmpty(message = "Brand name cannot be empty.")
    @Size(min = 2, max = 10, message = "Brand name must be between 2 and 14 characters.")
    private String name;
}
