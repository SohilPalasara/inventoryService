package com.inventoryService.inventoryService.service.impl;

import com.inventoryService.inventoryService.dto.OrganizationDto;
import com.inventoryService.inventoryService.dto.UserDto;
import com.inventoryService.inventoryService.entity.Organization;
import com.inventoryService.inventoryService.entity.User;
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
    private UserRepository userRepository;

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
                    "user saved successfully"
            );
        } catch (Exception e) {

            return ResponseModel.create(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null, "An error occurred:  sir"
            );
        }
    }

    @Override
    public ResponseEntity getAllUser() {
        try {
            Optional<List<User>> users = userRepository.findByIsDeletedFalse();

            if (users.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No user Found");
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

    @Override
    public ResponseEntity<?> getByUser(String userId) {
        try {
            Optional<User> user = userRepository.findByUserIdAndIsDeletedFalse(userId);

            if (user.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No users found");
            }

            UserDto userDto = UserDto.convertToDto(user.get());

            return ResponseEntity.ok(userDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    @Override
    public ResponseModel deleteUser(String userId) {
        try {
            Optional<User> user = userRepository.findByUserIdAndIsDeletedFalse(userId);
            if (user.isEmpty()) {
                return ResponseModel.create(
                        HttpStatus.NOT_FOUND,
                        null,
                        "user not found  or deleted"
                );
            }


            User users = user.get();
            users.setDeleted(true);
            userRepository.save(users);

            return ResponseModel.create(
                    HttpStatus.OK,
                    UserDto.convertToDto(users),
                    "user deleted successfully"
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
        public ResponseModel updateUser(String userId, UserDto userdto){
            try {
                Optional<User> users = userRepository.findByUserIdAndIsDeletedFalse(userId);

                if (users.isEmpty()) {
                    return ResponseModel.create(HttpStatus.NOT_FOUND, null, "user not found or deleted");
                }


                User user = users.get();
                userdto.updateEntity(user);

                User updated = userRepository.save(user);

                return ResponseModel.create(
                        HttpStatus.OK,
                        UserDto.convertToDto(updated),
                        "user updated successfully"
                );

            } catch (Exception e) {
                return ResponseModel.create(HttpStatus.INTERNAL_SERVER_ERROR, null, "Update failed: " + e.getMessage());
            }
        }


}

