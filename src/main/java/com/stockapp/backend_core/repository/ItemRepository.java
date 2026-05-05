package com.stockapp.backend_core.repository;

import com.stockapp.backend_core.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}