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
public class Product {
    private String productId;
    private String productName;
    private String description;
    private String productCode;
    private String productImage;

    private  boolean isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    private Category category;
    @ManyToOne
    private Organization organization;

    @PrePersist
    private void setValue(){
        this.productId= UUID.randomUUID().toString();
        this.createdAt=LocalDateTime.now();
        this.updatedAt=LocalDateTime.now();
    }
}
