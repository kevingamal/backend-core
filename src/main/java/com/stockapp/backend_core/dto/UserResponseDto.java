package com.stockapp.backend_core.dto;

import com.stockapp.backend_core.entity.enums.UserRole;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
    private UserRole role;
    private Boolean isActive;
}
