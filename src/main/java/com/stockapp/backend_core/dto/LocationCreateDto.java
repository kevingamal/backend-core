package com.stockapp.backend_core.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationCreateDto {
    @NotBlank(message = "El nombre es obligatorio")
    private String name;
    private String img;
    private String description;
    
    @NotNull(message = "El usuario creador es obligatorio")
    private Long createdByUserId;
}
