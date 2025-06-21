package com.inventoryService.inventoryService.service.impl;

import com.inventoryService.inventoryService.dto.OrganizationDto;
import com.inventoryService.inventoryService.entity.Organization;
import com.inventoryService.inventoryService.repository.OrganizationRepository;
import com.inventoryService.inventoryService.service.OrganizationService;
import com.inventoryService.inventoryService.utills.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    public ResponseModel registerOrganization(OrganizationDto organizationDto) {
        try {
            Optional<Organization> exist = organizationRepository.findByIsDeletedAndGstNoOrMobileNumber(
                    false,
                    organizationDto.getGstNo(),
                    organizationDto.getMobileNumber()
            );
            if (exist.isPresent()) {
                return ResponseModel.create(
                        HttpStatus.FOUND,
                        null,
                        "Organization already exists with this GST number or mobile number or ownerName"
                );
            }
            Organization organization = organizationDto.convertToEntity();
            organizationRepository.save(organization);
            return ResponseModel.create(
                    HttpStatus.OK,
                    OrganizationDto.convertToDto(organization),
                    "Organization saved successfully"
            );
        } catch (Exception e) {

            return ResponseModel.create(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null, "An error occurred: "
            );
        }
    }


    public ResponseEntity getAllOrganization() {
        try {
          Optional<List<Organization>> organizations = organizationRepository.findByIsDeletedFalse();

            if (organizations.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Organizations Found");
            }

            List<OrganizationDto> organizationDtoList = organizations.get().stream()
                    .map(OrganizationDto::convertToDto)
                    .toList();

            return ResponseEntity.ok(organizationDtoList);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    public ResponseEntity<?> getByOrganization(String organizationId) {
        try {
            Optional<Organization> organizations = organizationRepository.findByOrganizationIdAndIsDeletedFalse(organizationId);

            if (organizations.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No users found");
            }

            OrganizationDto organizationDto = OrganizationDto.convertToDto(organizations.get());

            return ResponseEntity.ok(organizationDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    public ResponseModel deleteOrganization(String organizationId) {
        try {
            Optional<Organization> organization = organizationRepository.findByOrganizationIdAndIsDeletedFalse(organizationId );
            if (organization.isEmpty()) {
                return ResponseModel.create(
                        HttpStatus.NOT_FOUND,
                        null,
                        "Organization not found  or deleted"
                );
            }


Organization organization1 = organization.get();
            organization1.setDeleted(true);
            organizationRepository.save(organization.get());
            OrganizationDto organizationDto = OrganizationDto.convertToDto(organization.get());
            return ResponseModel.create(
                    HttpStatus.OK,
                    organizationDto,
                    "Organization deleted successfully"
            );
        } catch (Exception e) {
            return ResponseModel.create(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    "An error occurred while deleting: " + e.getMessage()
            );

        }
    }
    public ResponseModel updateOrganization(String organizationId, OrganizationDto organizationDto) {
        try {
            Optional<Organization> organization = organizationRepository.findByOrganizationIdAndIsDeletedFalse(organizationId);

            if (organization.isEmpty()) {
                return ResponseModel.create(HttpStatus.NOT_FOUND, null, "Organization not found or deleted");
            }


            organizationDto.updateEntity(organization.get());

            Organization updated = organizationRepository.save(organization.get());

            return ResponseModel.create(
                    HttpStatus.OK,
                    OrganizationDto.convertToDto(updated),
                    "Organization updated successfully"
            );

        } catch (Exception e) {
            return ResponseModel.create(HttpStatus.INTERNAL_SERVER_ERROR, null, "Update failed: " + e.getMessage());
        }
    }

    public ResponseEntity<?> searchOrganizations(String keyword) {
        try {
            Optional<List<Organization>> organizations = organizationRepository
                    .findByIsDeletedFalseAndOrganizationNameContainingIgnoreCaseOrGstNoContainingIgnoreCaseOrMobileNumberContainingIgnoreCase(
                            keyword, keyword, keyword
                    );
            if (organizations.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No matching organizations found.");
            }

            List<OrganizationDto > organizationDtoList = organizations.get().stream()
                    .map(organization -> OrganizationDto.convertToDto(organization))
                    .toList();

        return ResponseEntity.ok(organizationDtoList);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error while searching: " + e.getMessage());
    }
    }
    public ResponseEntity<?> searchOrganizationsType(String key , String value) {
        try {
//            OrganizationDto organizationDto = new OrganizationDto();
//            List<Organization> organizations=organizationRepository.findByIsDeletedFalseAndOrganizationNameContainingIgnoreCase(keyword);
                 List<Organization> organizations;

            if (key.equalsIgnoreCase("name")) {
                organizations = organizationRepository.findByIsDeletedFalseAndOrganizationNameContainingIgnoreCase(value);
            } else {
                return ResponseEntity.badRequest().body("❌ Invalid search type. Only 'name' is supported right now.");
            }
            if (organizations.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No matching organizations found abc.");
            }
            List<OrganizationDto > organizationDtoList = organizations.stream()
                    .map(organization -> OrganizationDto.convertToDto(organization))
                    .toList();

            return ResponseEntity.ok(organizationDtoList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while searching: " + e.getMessage());
        }
    }
}


