package com.stockapp.backend_core.controller;

import com.stockapp.backend_core.dto.LocationCreateDto;
import com.stockapp.backend_core.dto.LocationResponseDto;
import com.stockapp.backend_core.service.LocationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping
    public LocationResponseDto create(
            @Valid @RequestBody LocationCreateDto dto
    ) {
        return locationService.create(dto);
    }

    @GetMapping
    public List<LocationResponseDto> findAll(
        @RequestParam(required = false) Boolean active
    ) {
        if (active != null) {
            return locationService.findByActive(active);
        }

        return locationService.findAll();
    }

    @GetMapping("/{id}")
    public LocationResponseDto findById(
            @PathVariable Long id
    ) {
        return locationService.findById(id);
    }

    @PutMapping("/{id}")
    public LocationResponseDto update(
            @PathVariable Long id,
            @Valid @RequestBody LocationCreateDto dto
    ) {
        return locationService.update(id, dto);
    }
    
    @PatchMapping("/{id}/activate")
    public void activate(
            @PathVariable Long id
    ) {
        locationService.activate(id);
    }

    @DeleteMapping("/{id}")
    public void deactivate(
            @PathVariable Long id
    ) {
        locationService.deactivate(id);
    }
}