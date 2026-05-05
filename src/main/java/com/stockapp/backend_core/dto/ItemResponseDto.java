package com.stockapp.backend_core.dto;

import com.stockapp.backend_core.entity.enums.ItemType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponseDto {
    private Long id;
    private String name;
    private String img;
    private String description;
    private ItemType type;
    private String presentation;
    private Integer initialStock;
    private Integer currentStock;
    private Boolean isActive;

    private Long currentLocationId;
    private String currentLocationName;

    private Long createdByUserId;
    private String createdByUserName;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
