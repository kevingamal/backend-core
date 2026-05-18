package com.stockapp.backend_core.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDto {

    private String token;
    private UserResponseDto user;
}