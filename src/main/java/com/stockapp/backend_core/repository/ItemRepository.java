package com.stockapp.backend_core.repository;

import com.stockapp.backend_core.entity.Item;
import com.stockapp.backend_core.entity.enums.ItemType;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

	List<Item> findByType(ItemType type);

	List<Item> findByCurrentLocationId(Long locationId);

	List<Item> findByIsActive(Boolean isActive);
}