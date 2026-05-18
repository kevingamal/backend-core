package com.stockapp.backend_core.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationResponseDto {
    private Long id;
    private String name;
    private String img;
    private String description;
    private Boolean isActive;

    private Long createdByUserId;
    private String createdByUserName;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}