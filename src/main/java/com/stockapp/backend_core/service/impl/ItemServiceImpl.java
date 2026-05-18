package com.stockapp.backend_core.service.impl;

import com.stockapp.backend_core.dto.ItemCreateDto;
import com.stockapp.backend_core.dto.ItemResponseDto;
import com.stockapp.backend_core.entity.Item;
import com.stockapp.backend_core.entity.enums.ItemType;
import com.stockapp.backend_core.entity.Location;
import com.stockapp.backend_core.entity.User;
import com.stockapp.backend_core.repository.ItemRepository;
import com.stockapp.backend_core.repository.LocationRepository;
import com.stockapp.backend_core.repository.UserRepository;
import com.stockapp.backend_core.service.ItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;

    public ItemServiceImpl(
            ItemRepository itemRepository,
            LocationRepository locationRepository,
            UserRepository userRepository
    ) {
        this.itemRepository = itemRepository;
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ItemResponseDto create(ItemCreateDto dto) {
        Location currentLocation = locationRepository.findById(dto.getCurrentLocationId())
                .orElseThrow(() -> new RuntimeException("Ubicación no encontrada"));

        User createdByUser = userRepository.findById(dto.getCreatedByUserId())
                .orElseThrow(() -> new RuntimeException("Usuario creador no encontrado"));

        Item item = new Item();
        item.setName(dto.getName());
        item.setImg(dto.getImg());
        item.setDescription(dto.getDescription());
        item.setType(dto.getType());
        item.setPresentation(dto.getPresentation());
        item.setInitialStock(dto.getInitialStock());
        item.setCurrentStock(dto.getInitialStock());
        item.setCurrentLocation(currentLocation);
        item.setCreatedByUser(createdByUser);
        item.setIsActive(true);

        Item savedItem = itemRepository.save(item);

        return mapToResponseDto(savedItem);
    }

    @Override
    public List<ItemResponseDto> findAll() {
        return itemRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    public ItemResponseDto findById(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));

        return mapToResponseDto(item);
    }
    
    @Override
    public List<ItemResponseDto> findByType(String type) {
    	ItemType itemType = ItemType.valueOf(type.toUpperCase());

 	   return itemRepository.findByType(itemType)
            .stream()
            .map(this::mapToResponseDto)
            .toList();
    }

    @Override
    public List<ItemResponseDto> findByLocationId(Long locationId) {
	    return itemRepository.findByCurrentLocationId(locationId)
 	           .stream()
 	           .map(this::mapToResponseDto)
	            .toList();
    }

    @Override
    public List<ItemResponseDto> findByActive(Boolean active) {
	    return itemRepository.findByIsActive(active)
	            .stream()
	            .map(this::mapToResponseDto)
	            .toList();
    }

    @Override
    public ItemResponseDto update(Long id, ItemCreateDto dto) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));

        Location currentLocation = locationRepository.findById(dto.getCurrentLocationId())
                .orElseThrow(() -> new RuntimeException("Ubicación no encontrada"));

        User createdByUser = userRepository.findById(dto.getCreatedByUserId())
                .orElseThrow(() -> new RuntimeException("Usuario creador no encontrado"));

        item.setName(dto.getName());
        item.setImg(dto.getImg());
        item.setDescription(dto.getDescription());
        item.setType(dto.getType());
        item.setPresentation(dto.getPresentation());
        item.setInitialStock(dto.getInitialStock());
        item.setCurrentLocation(currentLocation);
        item.setCreatedByUser(createdByUser);

        Item updatedItem = itemRepository.save(item);

        return mapToResponseDto(updatedItem);
    }
    
    @Override
    public void activate(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));

        item.setIsActive(true);

        itemRepository.save(item);
    }

    @Override
    public void deactivate(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));

        item.setIsActive(false);

        itemRepository.save(item);
    }

    private ItemResponseDto mapToResponseDto(Item item) {
        Long currentLocationId = null;
        String currentLocationName = null;

        if (item.getCurrentLocation() != null) {
            currentLocationId = item.getCurrentLocation().getId();
            currentLocationName = item.getCurrentLocation().getName();
        }

        Long createdByUserId = null;
        String createdByUserName = null;

        if (item.getCreatedByUser() != null) {
            createdByUserId = item.getCreatedByUser().getId();
            createdByUserName = item.getCreatedByUser().getName();
        }

        return new ItemResponseDto(
                item.getId(),
                item.getName(),
                item.getImg(),
                item.getDescription(),
                item.getType(),
                item.getPresentation(),
                item.getInitialStock(),
                item.getCurrentStock(),
                item.getIsActive(),
                currentLocationId,
                currentLocationName,
                createdByUserId,
                createdByUserName,
                item.getCreatedAt(),
                item.getUpdatedAt()
        );
    }
}