package com.stockapp.backend_core.service;

import com.stockapp.backend_core.dto.ItemCreateDto;
import com.stockapp.backend_core.dto.ItemResponseDto;

import java.util.List;

public interface ItemService {

    ItemResponseDto create(ItemCreateDto dto);

    List<ItemResponseDto> findAll();

    ItemResponseDto findById(Long id);

    ItemResponseDto update(Long id, ItemCreateDto dto);

    void deactivate(Long id);
}
