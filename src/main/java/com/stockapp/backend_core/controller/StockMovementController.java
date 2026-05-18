package com.stockapp.backend_core.controller;

import com.stockapp.backend_core.dto.StockMovementCreateDto;
import com.stockapp.backend_core.dto.StockMovementResponseDto;
import com.stockapp.backend_core.service.StockMovementService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movements")
public class StockMovementController {

    private final StockMovementService stockMovementService;

    public StockMovementController(StockMovementService stockMovementService) {
        this.stockMovementService = stockMovementService;
    }

    @PostMapping
    public StockMovementResponseDto create(
            @Valid @RequestBody StockMovementCreateDto dto
    ) {
        return stockMovementService.create(dto);
    }

    @GetMapping
    public List<StockMovementResponseDto> findAll(
        @RequestParam(required = false) Long itemId,
        @RequestParam(required = false) Long userId,
        @RequestParam(required = false) String movementType,
        @RequestParam(required = false) Long fromLocationId,
        @RequestParam(required = false) Long toLocationId
    ) {
	    if (itemId != null && movementType != null) {
	        return stockMovementService.findByItemIdAndMovementType(
	                itemId,
	                movementType
	        );
	    }

	    if (itemId != null) {
	        return stockMovementService.findByItemId(itemId);
	    }

	    if (userId != null) {
	        return stockMovementService.findByUserId(userId);
	    }

	    if (movementType != null) {
	        return stockMovementService.findByMovementType(movementType);
	    }

	    if (fromLocationId != null) {
	        return stockMovementService.findByFromLocationId(fromLocationId);
	    }

	    if (toLocationId != null) {
	        return stockMovementService.findByToLocationId(toLocationId);
	    }

	    return stockMovementService.findAll();
    }

    @GetMapping("/{id}")
    public StockMovementResponseDto findById(
            @PathVariable Long id
    ) {
        return stockMovementService.findById(id);
    }

    @GetMapping("/item/{itemId}")
    public List<StockMovementResponseDto> findByItemId(
            @PathVariable Long itemId
    ) {
        return stockMovementService.findByItemId(itemId);
    }
}