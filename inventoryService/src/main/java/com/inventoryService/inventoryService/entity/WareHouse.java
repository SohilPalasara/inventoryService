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
public class WareHouse {
    @Id
    private  String wareHouseId;
    private String locationOrArea;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    private boolean isDeleted;
    @ManyToOne
    private User user;
    @ManyToOne
    private Organization organization;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @PrePersist
    public void setValue(){
        this.wareHouseId = UUID.randomUUID().toString();
        this.createdAt=LocalDateTime.now();
        this.updatedAt=LocalDateTime.now();

    }


}
