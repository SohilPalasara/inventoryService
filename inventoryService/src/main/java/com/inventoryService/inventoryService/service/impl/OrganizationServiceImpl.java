package com.inventoryService.inventoryService.service.impl;

import com.inventoryService.inventoryService.dto.OrganizationDto;
import com.inventoryService.inventoryService.entity.Organization;
import com.inventoryService.inventoryService.enums.Status;
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
            Optional<Organization> organization = organizationRepository.findByOrganizationIdAndIsDeletedFalse(organizationId);
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

    public ResponseEntity<?> searchOrganizations(String value) {
        try {

            Optional<List<Organization>> organizations = organizationRepository
                    .searchByKeyword(value);
            if (organizations.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No matching organizations found.");
            }

            List<OrganizationDto> organizationDtoList = organizations.get().stream()
                    .map(organization -> OrganizationDto.convertToDto(organization))
                    .toList();

            return ResponseEntity.ok(organizationDtoList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while searching: " + e.getMessage());
        }
    }
//
//    public ResponseEntity<?> searchOrganizationsType(String key, String value) {
//        try {
//            List<Organization> organizations;
//
//
//                organizations = organizationRepository.findByIsDeletedFalseAndOrganizationNameContainingIgnoreCase(value);
//
//                organizations = organizationRepository.findByIsDeletedFalseAndGstNoContainingIgnoreCase(value);
//
//                organizations = organizationRepository.findByIsDeletedFalseAndMobileNumberContainingIgnoreCase(value);
//
//
//            if (organizations.isEmpty()) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No matching organizations found abc.");
//            }
//            List<OrganizationDto> organizationDtoList = organizations.stream()
//                    .map(organization -> OrganizationDto.convertToDto(organization))
//                    .toList();
//
//            return ResponseEntity.ok(organizationDtoList);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error while searching: " + e.getMessage());
//        }
//  //  }

//  public ResponseEntity<?> updateOrganizationStatus(String organizationId, Status status) {
//    try {
//        Optional<Organization> organizationOpt = organizationRepository
//                .findByOrganizationIdAndIsDeletedFalse(organizationId);
//
//        if (organizationOpt.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Organization not found");
//        }
//
//        Organization organization = organizationOpt.get();
//        organization.setStatus(status);
//
//        organizationRepository.save(organization);
//
//        // Return based on new status
//        String response = (status == Status.ACTIVE) ? "yes" : "no";
//        return ResponseEntity.ok(response);
//
//    } catch (Exception e) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body("Error updating status: " + e.getMessage());
//    }
//}

//    public ResponseEntity<?> updateOrganizationStatus(String organizationId ) {
//        try {
//            Optional<Organization> organization = organizationRepository
//                    .findByOrganizationIdAndIsDeletedFalse(organizationId);
//
//            if (organization.isEmpty()) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Organization not found");
//            }
//
//            Organization organizations =organization.get();
//            Status currentStatus = organizations.getStatus();
//            String responseMessage;
//
//            if (currentStatus == Status.ACTIVE) {
//                organizations.setStatus(Status.INACTIVE);
//                responseMessage = "no";
//            } else if (currentStatus == Status.INACTIVE) {
//                organizations.setStatus(Status.ACTIVE);
//                responseMessage = "yes";
//            } else {
//                return ResponseEntity.badRequest().body("Invalid current status");
//            }
//
//            organizationRepository.save(organizations);
//            return ResponseEntity.ok(responseMessage);
//
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error updating status: " + e.getMessage());
//        }
//    }

    public ResponseEntity<?> updateOrganizationStatus(String organizationId, Status inputStatus) {
        try {
            Optional<Organization> organizationOpt = organizationRepository
                    .findByOrganizationIdAndIsDeletedFalse(organizationId);

            if (organizationOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Organization not found");
            }

            Organization organization = organizationOpt.get();


            if (inputStatus == Status.ACTIVE) {
                organization.setStatus(Status.INACTIVE);
            } else if (inputStatus == Status.INACTIVE) {
                organization.setStatus(Status.ACTIVE);
            } else {
                return ResponseEntity.badRequest().body("Invalid status input");
            }

            organizationRepository.save(organization);

            // Prepare response after saving
            String response = (organization.getStatus() == Status.ACTIVE) ? "yes" : "no";
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating status: " + e.getMessage());
        }
    }

}


