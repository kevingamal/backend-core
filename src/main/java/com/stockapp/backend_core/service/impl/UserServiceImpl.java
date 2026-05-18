package com.stockapp.backend_core.service.impl;

import com.stockapp.backend_core.dto.UserCreateDto;
import com.stockapp.backend_core.dto.UserResponseDto;
import com.stockapp.backend_core.entity.User;
import com.stockapp.backend_core.repository.UserRepository;
import com.stockapp.backend_core.service.UserService;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder
    ) {
       this.userRepository = userRepository;
       this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponseDto create(UserCreateDto dto) {
        User user = new User();

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());
        user.setIsActive(true);

        User savedUser = userRepository.save(user);

        return mapToResponseDto(savedUser);
    }
    
    @Override
    public UserResponseDto update(Long id, UserCreateDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());

        User updatedUser = userRepository.save(user);

        return mapToResponseDto(updatedUser);
    }
    
    @Override
    public void activate(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setIsActive(true);

        userRepository.save(user);
    }

    @Override
    public void deactivate(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setIsActive(false);

        userRepository.save(user);
    }

    @Override
    public List<UserResponseDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }
    
    @Override
    public List<UserResponseDto> findByActive(Boolean active) {
        return userRepository.findByIsActive(active)
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
                user.getRole(),
                user.getIsActive()
        );
    }
}