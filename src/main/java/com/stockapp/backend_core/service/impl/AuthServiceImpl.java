package com.stockapp.backend_core.service.impl;

import com.stockapp.backend_core.dto.AuthLoginDto;
import com.stockapp.backend_core.dto.AuthResponseDto;
import com.stockapp.backend_core.dto.UserResponseDto;
import com.stockapp.backend_core.entity.User;
import com.stockapp.backend_core.repository.UserRepository;
import com.stockapp.backend_core.security.JwtService;
import com.stockapp.backend_core.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.stockapp.backend_core.exception.UnauthorizedException;
import com.stockapp.backend_core.exception.BadRequestException;
import com.stockapp.backend_core.exception.NotFoundException;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public AuthResponseDto login(AuthLoginDto dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Credenciales inválidas"));

        if (!Boolean.TRUE.equals(user.getIsActive())) {
            throw new BadRequestException("Usuario inactivo");
        }

        if (!passwordEncoder.matches(dto.getPassword(), user.getPasswordHash())) {
            throw new UnauthorizedException("Credenciales inválidas");
        }

        String token = jwtService.generateToken(user);

        return new AuthResponseDto(
                token,
                mapToUserResponseDto(user)
        );
    }

    @Override
    public UserResponseDto me(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        return mapToUserResponseDto(user);
    }

    private UserResponseDto mapToUserResponseDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getIsActive()
        );
    }
}