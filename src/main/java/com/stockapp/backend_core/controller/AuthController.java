package com.stockapp.backend_core.controller;

import com.stockapp.backend_core.dto.AuthLoginDto;
import com.stockapp.backend_core.dto.AuthResponseDto;
import com.stockapp.backend_core.dto.UserResponseDto;
import com.stockapp.backend_core.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public AuthResponseDto login(
            @Valid @RequestBody AuthLoginDto dto
    ) {
        return authService.login(dto);
    }

    @GetMapping("/me")
    public UserResponseDto me(Authentication authentication) {
        return authService.me(authentication.getName());
    }
}