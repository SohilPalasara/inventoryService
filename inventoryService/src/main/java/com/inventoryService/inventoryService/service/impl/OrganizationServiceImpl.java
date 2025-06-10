package com.inventoryService.inventoryService.service.impl;

import com.inventoryService.inventoryService.dto.OrganizationDto;
import com.inventoryService.inventoryService.entity.Organization;
import com.inventoryService.inventoryService.repository.OrganizationRepository;
import com.inventoryService.inventoryService.service.OrganizationService;
import com.inventoryService.inventoryService.utills.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    public ResponseModel registerOrganization(OrganizationDto organizationDto) {
        Organization exist = organizationRepository.findByGstNoOrMobileNumber(
                organizationDto.getGstNo(),
                organizationDto.getMobileNumber()
        );
        if (exist != null) {
            return ResponseModel.create(
                    HttpStatus.NOT_FOUND,
                    null,
                    "Organization already exists with this GST number or mobile number or ownerName"
            );
        }
        Organization organization = organizationDto.convertToEntity();
        organizationRepository.save(organization);
        OrganizationDto savedDto = OrganizationDto.convertToDto(organization);
        return ResponseModel.create(
                HttpStatus.OK,
                OrganizationDto.convertToDto(organization),
                "Organization saved successfully"
        );
    }
}
