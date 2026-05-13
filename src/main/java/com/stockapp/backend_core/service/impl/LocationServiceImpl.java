package com.stockapp.backend_core.service.impl;

import com.stockapp.backend_core.dto.LocationCreateDto;
import com.stockapp.backend_core.dto.LocationResponseDto;
import com.stockapp.backend_core.entity.Location;
import com.stockapp.backend_core.entity.User;
import com.stockapp.backend_core.repository.LocationRepository;
import com.stockapp.backend_core.repository.UserRepository;
import com.stockapp.backend_core.service.LocationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final UserRepository userRepository;

    public LocationServiceImpl(
            LocationRepository locationRepository,
            UserRepository userRepository
    ) {
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public LocationResponseDto create(LocationCreateDto dto) {
        User createdByUser = userRepository.findById(dto.getCreatedByUserId())
                .orElseThrow(() -> new RuntimeException("Usuario creador no encontrado"));

        Location location = new Location();
        location.setName(dto.getName());
        location.setImg(dto.getImg());
        location.setDescription(dto.getDescription());
        location.setIsActive(true);
        location.setCreatedByUser(createdByUser);

        Location savedLocation = locationRepository.save(location);

        return mapToResponseDto(savedLocation);
    }

    @Override
    public List<LocationResponseDto> findAll() {
        return locationRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    public LocationResponseDto findById(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Locación no encontrada"));

        return mapToResponseDto(location);
    }
    
    @Override
    public List<LocationResponseDto> findByActive(Boolean active) {
	    return locationRepository.findByIsActive(active)
	            .stream()
	            .map(this::mapToResponseDto)
	            .toList();
    }

    @Override
    public LocationResponseDto update(Long id, LocationCreateDto dto) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Locación no encontrada"));

        location.setName(dto.getName());
        location.setImg(dto.getImg());
        location.setDescription(dto.getDescription());

        Location updatedLocation = locationRepository.save(location);

        return mapToResponseDto(updatedLocation);
    }
    
    @Override
    public void activate(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ubicación no encontrada"));

        location.setIsActive(true);

        locationRepository.save(location);
    }

    @Override
    public void deactivate(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Locación no encontrada"));

        location.setIsActive(false);

        locationRepository.save(location);
    }

    private LocationResponseDto mapToResponseDto(Location location) {
        Long createdByUserId = null;
        String createdByUserName = null;

        if (location.getCreatedByUser() != null) {
            createdByUserId = location.getCreatedByUser().getId();
            createdByUserName = location.getCreatedByUser().getName();
        }

        return new LocationResponseDto(
                location.getId(),
                location.getName(),
                location.getImg(),
                location.getDescription(),
                location.getIsActive(),
                createdByUserId,
                createdByUserName,
                location.getCreatedAt(),
                location.getUpdatedAt()
        );
    }
}
