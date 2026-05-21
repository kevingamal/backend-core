package com.stockapp.backend_core.service.impl;

import com.stockapp.backend_core.dto.StockMovementCreateDto;
import com.stockapp.backend_core.dto.StockMovementResponseDto;
import com.stockapp.backend_core.entity.Item;
import com.stockapp.backend_core.entity.Location;
import com.stockapp.backend_core.entity.StockMovement;
import com.stockapp.backend_core.entity.User;
import com.stockapp.backend_core.entity.enums.ItemType;
import com.stockapp.backend_core.entity.enums.MovementType;
import com.stockapp.backend_core.repository.ItemRepository;
import com.stockapp.backend_core.repository.LocationRepository;
import com.stockapp.backend_core.repository.StockMovementRepository;
import com.stockapp.backend_core.repository.UserRepository;
import com.stockapp.backend_core.service.StockMovementService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.stockapp.backend_core.exception.BadRequestException;
import com.stockapp.backend_core.exception.NotFoundException;

import java.util.List;

@Service
public class StockMovementServiceImpl implements StockMovementService {

    private final StockMovementRepository stockMovementRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    public StockMovementServiceImpl(
            StockMovementRepository stockMovementRepository,
            ItemRepository itemRepository,
            UserRepository userRepository,
            LocationRepository locationRepository
    ) {
        this.stockMovementRepository = stockMovementRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    @Transactional
    public StockMovementResponseDto create(StockMovementCreateDto dto) {
        Item item = itemRepository.findById(dto.getItemId())
                .orElseThrow(() -> new NotFoundException("Item no encontrado"));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        Location fromLocation = null;
        if (dto.getFromLocationId() != null) {
            fromLocation = locationRepository.findById(dto.getFromLocationId())
                    .orElseThrow(() -> new NotFoundException("Ubicación de origen no encontrada"));
        }

        Location toLocation = null;
        if (dto.getToLocationId() != null) {
            toLocation = locationRepository.findById(dto.getToLocationId())
                    .orElseThrow(() -> new NotFoundException("Ubicación de destino no encontrada"));
        }

        validateMovement(dto, item, fromLocation, toLocation);
        applyMovementToItem(dto, item, toLocation);

        StockMovement movement = new StockMovement();
        movement.setItem(item);
        movement.setUser(user);
        movement.setFromLocation(fromLocation);
        movement.setToLocation(toLocation);
        movement.setMovementType(dto.getMovementType());
        movement.setQuantity(dto.getQuantity());
        movement.setNote(dto.getNote());

        itemRepository.save(item);
        StockMovement savedMovement = stockMovementRepository.save(movement);

        return mapToResponseDto(savedMovement);
    }

    @Override
    public List<StockMovementResponseDto> findAll() {
        return stockMovementRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    public StockMovementResponseDto findById(Long id) {
        StockMovement movement = stockMovementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Movimiento no encontrado"));

        return mapToResponseDto(movement);
    }

    @Override
    public List<StockMovementResponseDto> findByItemId(Long itemId) {
        return stockMovementRepository.findByItemId(itemId)
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }
    
    @Override
    public List<StockMovementResponseDto> findByUserId(Long userId) {
	    return stockMovementRepository.findByUserId(userId)
	            .stream()
	            .map(this::mapToResponseDto)
	            .toList();
    }

    @Override
    public List<StockMovementResponseDto> findByMovementType(String movementType) {
	    MovementType type = MovementType.valueOf(movementType.toUpperCase());

	    return stockMovementRepository.findByMovementType(type)
	            .stream()
	            .map(this::mapToResponseDto)
	            .toList();
    }

    @Override
    public List<StockMovementResponseDto> findByFromLocationId(Long fromLocationId) {
	    return stockMovementRepository.findByFromLocationId(fromLocationId)
	            .stream()
	            .map(this::mapToResponseDto)
	            .toList();
    }

    @Override
    public List<StockMovementResponseDto> findByToLocationId(Long toLocationId) {
	    return stockMovementRepository.findByToLocationId(toLocationId)
        	    .stream()
        	    .map(this::mapToResponseDto)
        	    .toList();
    }

    @Override
    public List<StockMovementResponseDto> findByItemIdAndMovementType(
	        Long itemId,
	        String movementType
    ) {
	    MovementType type = MovementType.valueOf(movementType.toUpperCase());

	    return stockMovementRepository
	            .findByItemIdAndMovementType(itemId, type)
	            .stream()
	            .map(this::mapToResponseDto)
	            .toList();
    }

    private void validateMovement(
            StockMovementCreateDto dto,
            Item item,
            Location fromLocation,
            Location toLocation
    ) {
        if (dto.getMovementType() == null) {
            throw new BadRequestException("El tipo de movimiento es obligatorio");
        }

        if (dto.getQuantity() == null || dto.getQuantity() <= 0) {
            throw new BadRequestException("La cantidad debe ser mayor a cero");
        }

        MovementType type = dto.getMovementType();

        switch (type) {
            case TRANSFER -> {
                if (fromLocation == null || toLocation == null) {
                    throw new BadRequestException("TRANSFER requiere ubicación de origen y destino");
                }

                if (item.getType() == ItemType.TOOL && dto.getQuantity() != 1) {
                    throw new BadRequestException("Una herramienta solo puede transferirse con cantidad 1");
                }
            }

            case CONSUMPTION -> {
                if (item.getType() != ItemType.SUPPLY) {
                    throw new BadRequestException("CONSUMPTION solo aplica a insumos");
                }

                if (toLocation == null) {
                    throw new BadRequestException("CONSUMPTION requiere ubicación donde se consumió");
                }

                if (item.getCurrentStock() < dto.getQuantity()) {
                    throw new BadRequestException("Stock insuficiente");
                }
            }

            case CREATE -> {
                if (fromLocation != null) {
                    throw new BadRequestException("CREATE no debe tener ubicación de origen");
                }

                if (toLocation == null) {
                    throw new BadRequestException("CREATE requiere ubicación inicial");
                }
            }

            case ADJUSTMENT_IN -> {
                if (fromLocation != null) {
                    throw new BadRequestException("ADJUSTMENT_IN no debe tener ubicación de origen");
                }

                if (toLocation == null) {
                    throw new BadRequestException("ADJUSTMENT_IN requiere ubicación donde apareció stock");
                }
            }

            case ADJUSTMENT_OUT -> {
                if (fromLocation == null) {
                    throw new BadRequestException("ADJUSTMENT_OUT requiere ubicación de origen");
                }

                if (toLocation != null) {
                    throw new BadRequestException("ADJUSTMENT_OUT no debe tener ubicación de destino");
                }

                if (item.getCurrentStock() < dto.getQuantity()) {
                    throw new BadRequestException("Stock insuficiente");
                }
            }
        }
    }

    private void applyMovementToItem(
            StockMovementCreateDto dto,
            Item item,
            Location toLocation
    ) {
        MovementType type = dto.getMovementType();

        switch (type) {
            case TRANSFER -> {
                item.setCurrentLocation(toLocation);
            }

            case CONSUMPTION -> {
                item.setCurrentStock(item.getCurrentStock() - dto.getQuantity());
            }

            case CREATE -> {
                item.setCurrentLocation(toLocation);
            }

            case ADJUSTMENT_IN -> {
                item.setCurrentStock(item.getCurrentStock() + dto.getQuantity());
            }

            case ADJUSTMENT_OUT -> {
                item.setCurrentStock(item.getCurrentStock() - dto.getQuantity());
            }
        }
    }

    private StockMovementResponseDto mapToResponseDto(StockMovement movement) {
        Long itemId = null;
        String itemName = null;

        if (movement.getItem() != null) {
            itemId = movement.getItem().getId();
            itemName = movement.getItem().getName();
        }

        Long userId = null;
        String userName = null;

        if (movement.getUser() != null) {
            userId = movement.getUser().getId();
            userName = movement.getUser().getName();
        }

        Long fromLocationId = null;
        String fromLocationName = null;

        if (movement.getFromLocation() != null) {
            fromLocationId = movement.getFromLocation().getId();
            fromLocationName = movement.getFromLocation().getName();
        }

        Long toLocationId = null;
        String toLocationName = null;

        if (movement.getToLocation() != null) {
            toLocationId = movement.getToLocation().getId();
            toLocationName = movement.getToLocation().getName();
        }

        return new StockMovementResponseDto(
                movement.getId(),
                itemId,
                itemName,
                userId,
                userName,
                fromLocationId,
                fromLocationName,
                toLocationId,
                toLocationName,
                movement.getMovementType(),
                movement.getQuantity(),
                movement.getNote(),
                movement.getCreatedAt()
        );
    }
}