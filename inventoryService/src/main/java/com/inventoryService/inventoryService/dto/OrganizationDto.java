package com.inventoryService.inventoryService.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.inventoryService.inventoryService.entity.Organization;
import com.inventoryService.inventoryService.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationDto {

    private String organizationName;
    private String gstNo;
    private String ownerName;
    private String mobileNumber;
    private String locationOrArea;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Organization convertToEntity() {
        return Organization.builder()
                .organizationName(organizationName)
                .gstNo(this.gstNo)
                .ownerName(this.ownerName)
                .mobileNumber(this.mobileNumber)
                .locationOrArea(this.locationOrArea)
                .status(Status.ACTIVE)
                .build();
    }

    public static OrganizationDto convertToDto(Organization organization) {

        return OrganizationDto.builder()
                .organizationName(organization.getOrganizationName())
                .gstNo(organization.getGstNo())
                .ownerName(organization.getOwnerName())
                .mobileNumber(organization.getMobileNumber())
                .locationOrArea(organization.getLocationOrArea())
                .status(organization.getStatus())
                .createdAt(organization.getCreatedAt())
                .updatedAt(organization.getUpdatedAt())
                .build();
    }

    public void updateEntity(Organization organization) {
        if (this.organizationName != null) {
            organization.setOrganizationName(this.organizationName);
        }
        if (this.gstNo != null) {
            organization.setGstNo(this.gstNo);
        }
        if (this.ownerName != null) {
            organization.setOwnerName(this.ownerName);
        }
        if (this.mobileNumber != null) {
            organization.setMobileNumber(this.mobileNumber);
        }
        if (this.locationOrArea != null) {
            organization.setLocationOrArea(this.locationOrArea);
        }
        if (this.status != null) {
            organization.setStatus(this.status);
        }

    }

}
