package com.inventoryService.inventoryService.entity;

import com.inventoryService.inventoryService.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Organization {
    @Id
    private String organizationId;
    private String organizationName;
    @Column(unique = true)
    private String gstNo;
    private String ownerName;
    @Column(unique = true)
    private String mobileNumber;
    private String locationOrArea;
    @Enumerated(EnumType.STRING)
    private Status status;
    private boolean isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void setValue() {
        this.organizationId = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
    }
    @PreUpdate
    public void updateTimestamp() {
        this.updatedAt = LocalDateTime.now();
    }

}
//private String organisationId; // British English
