package com.inventoryService.inventoryService.dto;

import com.inventoryService.inventoryService.entity.Organization;
import com.inventoryService.inventoryService.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private boolean isDeleted;


    public Organization convertToEntity() {
        return Organization.builder()

                .organizationName(organizationName)
                .gstNo(this.gstNo)
                .ownerName(this.ownerName)
                .mobileNumber(this.mobileNumber)
                .locationOrArea(this.locationOrArea)
                .status(Status.ACTIVE)
                .isDeleted(this.isDeleted)
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
                .isDeleted(organization.isDeleted())
                .build();
    }




}
