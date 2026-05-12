package com.stockapp.backend_core.controller;

import com.stockapp.backend_core.dto.ItemCreateDto;
import com.stockapp.backend_core.dto.ItemResponseDto;
import com.stockapp.backend_core.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ItemResponseDto create(
            @Valid @RequestBody ItemCreateDto dto
    ) {
        return itemService.create(dto);
    }

    @GetMapping
    public List<ItemResponseDto> findAll() {
        return itemService.findAll();
    }

    @GetMapping("/{id}")
    public ItemResponseDto findById(
            @PathVariable Long id
    ) {
        return itemService.findById(id);
    }

    @PutMapping("/{id}")
    public ItemResponseDto update(
            @PathVariable Long id,
            @Valid @RequestBody ItemCreateDto dto
    ) {
        return itemService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deactivate(
            @PathVariable Long id
    ) {
        itemService.deactivate(id);
    }
}
