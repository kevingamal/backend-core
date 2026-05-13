package com.stockapp.backend_core.repository;

import com.stockapp.backend_core.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {
	List<Location> findByIsActive(Boolean isActive);
}
