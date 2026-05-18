package com.stockapp.backend_core.service;

import com.stockapp.backend_core.dto.LocationCreateDto;
import com.stockapp.backend_core.dto.LocationResponseDto;

import java.util.List;

public interface LocationService {

    LocationResponseDto create(LocationCreateDto dto);

    List<LocationResponseDto> findAll();

    LocationResponseDto findById(Long id);
    
    List<LocationResponseDto> findByActive(Boolean active);

    LocationResponseDto update(Long id, LocationCreateDto dto);
    
    void activate(Long id);

    void deactivate(Long id);
}