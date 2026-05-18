package com.stockapp.backend_core.controller;

import com.stockapp.backend_core.dto.UserCreateDto;
import com.stockapp.backend_core.dto.UserResponseDto;
import com.stockapp.backend_core.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponseDto create(
            @Valid @RequestBody UserCreateDto dto
    ) {
        return userService.create(dto);
    }
    
    @PutMapping("/{id}")
    public UserResponseDto update(
            @PathVariable Long id,
            @Valid @RequestBody UserCreateDto dto
    ) {
        return userService.update(id, dto);
    }
    
    @PatchMapping("/{id}/activate")
    public void activate(
            @PathVariable Long id
    ) {
        userService.activate(id);
    }

    @DeleteMapping("/{id}")
    public void deactivate(
            @PathVariable Long id
    ) {
        userService.deactivate(id);
    }

    @GetMapping
    public List<UserResponseDto> findAll(
            @RequestParam(required = false) Boolean active
    ) {
        if (active != null) {
            return userService.findByActive(active);
        }

        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserResponseDto findById(
            @PathVariable Long id
    ) {
        return userService.findById(id);
    }
}