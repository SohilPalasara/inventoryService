package com.inventoryService.inventoryService.dto;

import com.inventoryService.inventoryService.entity.Organization;
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


    private String organizationId;

    public static WareHouseDto convertToDto(WareHouse wareHouse) {

        return WareHouseDto.builder()
                .wareHouseId(wareHouse.getWareHouseId())
                .locationOrArea(wareHouse.getLocationOrArea())
                .title(wareHouse.getTitle())
                .description(wareHouse.getDescription())
                .status(wareHouse.getStatus())
                .organizationId(wareHouse.getOrganization().getOrganizationId())
                .createdAt(wareHouse.getCreatedAt())
                .updatedAt(wareHouse.getUpdatedAt())
                .build();
    }

    public WareHouse convertToEntity(Organization organization) {

        return WareHouse.builder()

                .locationOrArea(this.locationOrArea)
                .title(this.title)
                .description(this.description)
                .status(Status.ACTIVE)
                .organization(organization)
                .build();
    }

    public void updateEntity(WareHouse wareHouses) {

        if (this.locationOrArea != null) {
            wareHouses.setLocationOrArea(this.locationOrArea);
        }
        if (this.title != null) {
            wareHouses.setTitle(this.title);
        }
        if (this.description != null) {
            wareHouses.setDescription(this.description);
        }


        if (this.status != null) {
            wareHouses.setStatus(Status.ACTIVE);
        }

    }
}
