package com.stockapp.backend_core.service;

import com.stockapp.backend_core.dto.StockMovementCreateDto;
import com.stockapp.backend_core.dto.StockMovementResponseDto;

import java.util.List;

public interface StockMovementService {

    StockMovementResponseDto create(StockMovementCreateDto dto);

    List<StockMovementResponseDto> findAll();

    StockMovementResponseDto findById(Long id);

    List<StockMovementResponseDto> findByItemId(Long itemId);
    
    List<StockMovementResponseDto> findByUserId(Long userId);

    List<StockMovementResponseDto> findByMovementType(String movementType);

    List<StockMovementResponseDto> findByFromLocationId(Long fromLocationId);

    List<StockMovementResponseDto> findByToLocationId(Long toLocationId);

    List<StockMovementResponseDto> findByItemIdAndMovementType(
        Long itemId,
        String movementType
    );
}
