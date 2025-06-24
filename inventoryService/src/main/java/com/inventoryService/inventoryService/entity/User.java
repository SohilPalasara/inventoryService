package com.inventoryService.inventoryService.entity;

import com.inventoryService.inventoryService.enums.Department;
import com.inventoryService.inventoryService.enums.Gender;
import com.inventoryService.inventoryService.enums.Role;
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
public class User {
    @Id
    private  String userId;
    @Column(nullable = false)
    private  String  fullName;
    @Column(nullable = false , unique = true)
    private  String  mobileNumber;
    @Column(nullable = false)
    private  String  password;
    private Role role;
    private Gender gender;
    private  boolean isDeleted;
    //optional
    private String  email;

    //optional
    @Enumerated(EnumType.STRING)
    private Department department;
    //optional
    private String profilePicture;

    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @ManyToOne
    private Organization organization;
    @PrePersist
    public void setValue(){
        this.userId= UUID.randomUUID().toString();
        this.createdAt=LocalDateTime.now();
        this.updatedAt=LocalDateTime.now();
    }
}
