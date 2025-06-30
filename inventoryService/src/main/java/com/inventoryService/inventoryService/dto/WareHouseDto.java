package com.inventoryService.inventoryService.dto;

import com.inventoryService.inventoryService.entity.Organization;
import com.inventoryService.inventoryService.entity.User;
import com.inventoryService.inventoryService.entity.WareHouse;
import com.inventoryService.inventoryService.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WareHouseDto {
    private String wareHouseId;
    private String locationOrArea;
    private String title;
    private String description;

    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String userId;
    private String organizationId;

//    public WareHouse convertToEntity() {
//
//
//        return WareHouse.builder()
//                .locationOrArea(this.locationOrArea)
//                .
//                .organization(organization)
//                .build();
//    }

}
