package com.stockapp.backend_core.entity;

import com.stockapp.backend_core.entity.enums.ItemType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String img;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ItemType type;

    private String presentation;

    private Integer initialStock;

    private Integer currentStock;

    private Boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "current_location_id")
    private Location currentLocation;

    @ManyToOne
    @JoinColumn(name = "created_by_user_id")
    private User createdByUser;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}