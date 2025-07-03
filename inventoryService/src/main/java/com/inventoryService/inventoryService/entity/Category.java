package com.inventoryService.inventoryService.entity;

import com.inventoryService.inventoryService.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {
    @Id
    private String id;
    private String categoryName;
    private String description;

    private  boolean isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    private Category parentCategory;
    @ManyToOne
    private Organization organization;

    @PrePersist
    public void setValue(){
        this.id = UUID.randomUUID().toString();
        this.createdAt=LocalDateTime.now();
        this.updatedAt=LocalDateTime.now();
    }
}
