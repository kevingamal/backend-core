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
    public List<StockMovementResponseDto> findAll() {
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
