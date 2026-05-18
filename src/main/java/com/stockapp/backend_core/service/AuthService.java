package com.stockapp.backend_core.service;

import com.stockapp.backend_core.dto.AuthLoginDto;
import com.stockapp.backend_core.dto.AuthResponseDto;
import com.stockapp.backend_core.dto.UserResponseDto;

public interface AuthService {

    AuthResponseDto login(AuthLoginDto dto);

    UserResponseDto me(String email);
}