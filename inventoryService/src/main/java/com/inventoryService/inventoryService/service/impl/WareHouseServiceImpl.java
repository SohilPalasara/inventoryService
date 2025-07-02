package com.inventoryService.inventoryService.service.impl;

import com.inventoryService.inventoryService.dto.UserDto;
import com.inventoryService.inventoryService.dto.WareHouseDto;
import com.inventoryService.inventoryService.entity.Organization;
import com.inventoryService.inventoryService.entity.User;
import com.inventoryService.inventoryService.entity.WareHouse;
import com.inventoryService.inventoryService.enums.Status;
import com.inventoryService.inventoryService.repository.OrganizationRepository;
import com.inventoryService.inventoryService.repository.UserRepository;
import com.inventoryService.inventoryService.repository.WareHouseRepository;
import com.inventoryService.inventoryService.service.WareHouseService;
import com.inventoryService.inventoryService.utills.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class WareHouseServiceImpl implements WareHouseService {
    @Autowired
    private WareHouseRepository wareHouseRepository;
    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public ResponseModel registerWareHouse(WareHouseDto wareHouseDto) {

        try {
//            Optional<User> exist = userRepository.findByIsDeletedAndMobileNumber(
//                    false,
//                    wareHouseDto.getMobileNumber()
//            );
//
//            if (exist.isPresent()) {
//                return ResponseModel.create(
//                        HttpStatus.FOUND,
//                        null,
//                        "User already exists with  mobile number "
//                );
//            }
            Optional<Organization> organization = organizationRepository.findByOrganizationIdAndIsDeletedFalse(wareHouseDto.getOrganizationId());
            if (organization.isEmpty()) {
                return ResponseModel.create(
                        HttpStatus.NOT_FOUND,
                        null,
                        "Organization not found"
                );
            }

            WareHouse wareHouse = wareHouseDto.convertToEntity(organization.get() );
            wareHouseRepository.save(wareHouse);
            return ResponseModel.create(
                    HttpStatus.OK,
                    WareHouseDto.convertToDto(wareHouse),
                    "wareHouse saved successfully"
            );
        } catch (Exception e) {

            return ResponseModel.create(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null, "An error occurred:  sir"
            );
        }
    }


    @Override
    public ResponseEntity<?> getAllWareHouse() {
        try {
            Optional<List<WareHouse>> wareHouses= wareHouseRepository.findByIsDeletedFalse();

            if (wareHouses.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No wareHouse Found");
            }

            List<WareHouseDto> wareHouseDtoList = wareHouses.get().stream()
                    .map(wareHouse ->WareHouseDto.convertToDto(wareHouse))
                    .toList();

            return ResponseEntity.ok(wareHouseDtoList);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }
    @Override
    public ResponseEntity<?> getByWareHouse(String wareHouseId) {
        try {
            Optional<WareHouse> wareHouse = wareHouseRepository.findByWareHouseIdAndIsDeletedFalse(wareHouseId);

            if (wareHouse.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No wareHouse found");
            }

            WareHouseDto wareHouseDto=WareHouseDto.convertToDto(wareHouse.get());

            return ResponseEntity.ok(wareHouseDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }
   @Override
    public ResponseModel deleteWareHouse(String wareHouseId) {
        try {
            Optional<WareHouse> wareHouse = wareHouseRepository.findByWareHouseIdAndIsDeletedFalse(wareHouseId);
            if (wareHouse.isEmpty()) {
                return ResponseModel.create(
                        HttpStatus.NOT_FOUND,
                        null,
                        "wareHouse not found  or deleted"
                );
            }


            WareHouse wareHouses = wareHouse.get();
            wareHouses.setDeleted(true);
            wareHouseRepository.save(wareHouses);

            return ResponseModel.create(
                    HttpStatus.OK,
                    WareHouseDto.convertToDto(wareHouses),
                    "wareHouse deleted successfully"
            );
        } catch (Exception e) {
            return ResponseModel.create(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    "An error occurred while deleting: " + e.getMessage()
            );

        }
    }

    @Override
    public ResponseModel updateWareHouse(String wareHouseId, WareHouseDto wareHouseDto) {
        try {
            Optional<WareHouse> wareHouse = wareHouseRepository.findByWareHouseIdAndIsDeletedFalse(wareHouseId);

            if (wareHouse.isEmpty()) {
                return ResponseModel.create(HttpStatus.NOT_FOUND, null, "user not found or deleted");
            }


            WareHouse wareHouses = wareHouse.get();
            wareHouseDto.updateEntity(wareHouses);

            WareHouse updated = wareHouseRepository.save(wareHouses);

            return ResponseModel.create(
                    HttpStatus.OK,
                    WareHouseDto.convertToDto(updated),
                    "user updated successfully"
            );

        } catch (Exception e) {
            return ResponseModel.create(HttpStatus.INTERNAL_SERVER_ERROR, null, "Update failed: " + e.getMessage());
        }
    }


    @Override
    public ResponseEntity<?> searchWareHouse(String value) {
        try {

            Optional<List<WareHouse>> wareHouses = wareHouseRepository
                    .searchByValue(value);
            if (wareHouses.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No matching organizations found.");
            }

            List<WareHouseDto> wareHouseDtoList = wareHouses.get().stream()
                    .map(wareHouse -> WareHouseDto.convertToDto(wareHouse))
                    .toList();

            return ResponseEntity.ok(wareHouseDtoList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while searching: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> updateWareHouseStatus(String wareHouseId, Status inputStatus) {
        try {
            Optional<WareHouse> wareHouse = wareHouseRepository
                    .findByWareHouseIdAndIsDeletedFalse(wareHouseId);

            if (wareHouse.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("wareHouse not found");
            }

            WareHouse wareHouses = wareHouse.get();
            Status currentStatus = wareHouses.getStatus();

            if (currentStatus.equals(inputStatus)) {
                return ResponseEntity.ok("Already " + inputStatus.name() + " Refresh For Update Data");
            }

            if (currentStatus.equals(Status.ACTIVE) && inputStatus.equals(Status.INACTIVE)) {
                wareHouses.setStatus(Status.INACTIVE);
            } else if (currentStatus.equals(Status.INACTIVE) && inputStatus.equals(Status.ACTIVE)) {
               wareHouses.setStatus(Status.ACTIVE);
            } else {
                return ResponseEntity.badRequest().body("Invalid status input");
            }

            wareHouseRepository.save(wareHouses);
            return ResponseEntity.ok("Successfully Updated");


        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating status: " + ex.getMessage());

        }
    }
}
