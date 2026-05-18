package com.stockapp.backend_core.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthLoginDto {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}