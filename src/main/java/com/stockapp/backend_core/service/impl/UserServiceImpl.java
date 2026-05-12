package com.stockapp.backend_core.service.impl;

import com.stockapp.backend_core.dto.UserCreateDto;
import com.stockapp.backend_core.dto.UserResponseDto;
import com.stockapp.backend_core.entity.User;
import com.stockapp.backend_core.repository.UserRepository;
import com.stockapp.backend_core.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDto create(UserCreateDto dto) {
        User user = new User();

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPasswordHash(dto.getPassword()); // Temporal: después se reemplaza por hash real
        user.setRole(dto.getRole());

        User savedUser = userRepository.save(user);

        return mapToResponseDto(savedUser);
    }

    @Override
    public List<UserResponseDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    public UserResponseDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return mapToResponseDto(user);
    }

    private UserResponseDto mapToResponseDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
}
