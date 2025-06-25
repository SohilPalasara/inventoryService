package com.inventoryService.inventoryService.service.impl;

import com.inventoryService.inventoryService.dto.OrganizationDto;
import com.inventoryService.inventoryService.dto.UserDto;
import com.inventoryService.inventoryService.entity.Organization;
import com.inventoryService.inventoryService.entity.User;
import com.inventoryService.inventoryService.enums.Department;
import com.inventoryService.inventoryService.repository.UserRepository;
import com.inventoryService.inventoryService.service.UserService;
import com.inventoryService.inventoryService.utills.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private  UserRepository userRepository;

    @Override
    public ResponseModel registerOrganization(UserDto userDto) {

        try {
            Optional<User> exist = userRepository.findByIsDeletedAndMobileNumber(
                    false,
                    userDto.getMobileNumber()
            );

            if (exist.isPresent()) {
                return ResponseModel.create(
                        HttpStatus.FOUND,
                        null,
                        "User already exists with  mobile number "
                );
            }

            User user = userDto.convertToEntity();
            userRepository.save(user);
            return ResponseModel.create(
                    HttpStatus.OK,
                   UserDto.convertToDto(user),
                    "Organization saved successfully"
            );
        } catch (Exception e) {

            return ResponseModel.create(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null, "An error occurred:  sir"
            );
        }
    }
    public ResponseEntity getAllUser() {
        try {
            Optional<List<User>> users = userRepository.findByIsDeletedFalse();

            if (users.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Organizations Found");
            }

            List<UserDto> userDtoList = users.get().stream()
                    .map(user -> UserDto.convertToDto(user))
                    .toList();

            return ResponseEntity.ok(userDtoList);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    }

