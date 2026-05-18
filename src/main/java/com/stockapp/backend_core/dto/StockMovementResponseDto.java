package com.stockapp.backend_core.dto;

import com.stockapp.backend_core.entity.enums.MovementType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockMovementResponseDto {
    private Long id;

    private Long itemId;
    private String itemName;

    private Long userId;
    private String userName;

    private Long fromLocationId;
    private String fromLocationName;

    private Long toLocationId;
    private String toLocationName;

    private MovementType movementType;
    private Integer quantity;
    private String note;
    private LocalDateTime createdAt;
}