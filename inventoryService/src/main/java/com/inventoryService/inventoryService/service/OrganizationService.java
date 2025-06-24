package com.inventoryService.inventoryService.service;

import com.inventoryService.inventoryService.dto.OrganizationDto;
import com.inventoryService.inventoryService.enums.Status;
import com.inventoryService.inventoryService.utills.ResponseModel;
import org.springframework.http.ResponseEntity;

public interface OrganizationService {

    ResponseModel registerOrganization(OrganizationDto organizationDto);

    ResponseEntity<?> getAllOrganization();

    ResponseEntity<?> getByOrganization(String organizationId);

    ResponseModel deleteOrganization(String organizationId);

    ResponseModel updateOrganization(String organizationId, OrganizationDto organizationDto);

    ResponseEntity<?> searchOrganizations(String value);

    ResponseEntity<?> updateOrganizationStatus(String organizationId, Status inputStatus);
}
