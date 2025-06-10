package com.inventoryService.inventoryService.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Status status;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean isDeleted;

    public static OrganizationDto convertToDto(Organization organization) {

        return OrganizationDto.builder()
                .organizationName(organization.getOrganizationName())
                .gstNo(organization.getGstNo())
                .ownerName(organization.getOwnerName())
                .mobileNumber(organization.getMobileNumber())
                .locationOrArea(organization.getLocationOrArea())

                .build();
    }

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


}
