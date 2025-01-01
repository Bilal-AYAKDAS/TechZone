package com.developerteam.techzone.entities.dto;

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
public class DtoChangePasswdIU {

    private String email;

    private String oldPasswd;

    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters.")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=.]).*$",
            message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character."
    )
    private String newPasswd;

    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters.")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=.]).*$",
            message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character."
    )
    private String confirmPasswd;

}
