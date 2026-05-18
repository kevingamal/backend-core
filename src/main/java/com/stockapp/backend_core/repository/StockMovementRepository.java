package com.stockapp.backend_core.repository;

import com.stockapp.backend_core.entity.StockMovement;
import com.stockapp.backend_core.entity.enums.MovementType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
    List<StockMovement> findByItemId(Long itemId);
    List<StockMovement> findByUserId(Long userId);
    List<StockMovement> findByMovementType(MovementType movementType);
    List<StockMovement> findByFromLocationId(Long fromLocationId);
    List<StockMovement> findByToLocationId(Long toLocationId);
    List<StockMovement> findByItemIdAndMovementType(
        Long itemId,
        MovementType movementType
    );
}