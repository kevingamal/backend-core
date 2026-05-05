package com.stockapp.backend_core.dto;

import com.stockapp.backend_core.entity.enums.ItemType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemCreateDto {
    @NotBlank(message = "El nombre es obligatorio")
    private String name;
    private String img;
    private String description;
    @NotNull(message = "El tipo de item es obligatorio")
    private ItemType type;
    @NotBlank(message = "La presentación es obligatoria")
    private String presentation;
    @NotNull(message = "El stock inicial es obligatorio")
    @Min(value = 0, message = "El stock inicial no puede ser negativo")
    private Integer initialStock;

    @NotNull(message = "La ubicación inicial es obligatoria")
    private Long currentLocationId;

    @NotNull(message = "El usuario creador es obligatorio")
    private Long createdByUserId;
}
