package com.stockapp.backend_core.service;

import com.stockapp.backend_core.dto.UserCreateDto;
import com.stockapp.backend_core.dto.UserResponseDto;

import java.util.List;

public interface UserService {

    UserResponseDto create(UserCreateDto dto);

    List<UserResponseDto> findAll();

    UserResponseDto findById(Long id);
}
