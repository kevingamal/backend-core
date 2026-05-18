package com.stockapp.backend_core.service;

import com.stockapp.backend_core.dto.UserCreateDto;
import com.stockapp.backend_core.dto.UserResponseDto;

import java.util.List;

public interface UserService {

    UserResponseDto create(UserCreateDto dto);

    List<UserResponseDto> findAll();
    
    List<UserResponseDto> findByActive(Boolean active);

    UserResponseDto findById(Long id);
    
    UserResponseDto update(Long id, UserCreateDto dto);
    
    void activate(Long id);
    
    void deactivate(Long id);
}