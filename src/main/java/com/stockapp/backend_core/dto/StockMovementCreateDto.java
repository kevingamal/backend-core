package com.stockapp.backend_core.dto;

import com.stockapp.backend_core.entity.enums.MovementType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockMovementCreateDto {
    @NotNull(message = "El item es obligatorio")
    private Long itemId;

    @NotNull(message = "El usuario es obligatorio")
    private Long userId;

    private Long fromLocationId;
    private Long toLocationId;
    
    @NotNull(message = "El tipo de movimiento es obligatorio")
    private MovementType movementType;
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor a cero")
    private Integer quantity;
    private String note;
}
