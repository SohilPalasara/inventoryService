package com.inventoryService.inventoryService.service.impl;

import com.inventoryService.inventoryService.dto.UserDto;
import com.inventoryService.inventoryService.entity.Organization;
import com.inventoryService.inventoryService.entity.User;
import com.inventoryService.inventoryService.enums.Status;
import com.inventoryService.inventoryService.repository.OrganizationRepository;
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
    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public ResponseModel registerUser(UserDto userDto) {

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
            Optional<Organization> organization = organizationRepository.findByOrganizationIdAndIsDeletedFalse(userDto.getOrganizationId());
            if (organization.isEmpty()) {
                return ResponseModel.create(
                        HttpStatus.NOT_FOUND,
                        null,
                        "Organization not found"
                );
            }
            User user = userDto.convertToEntity(organization.get());
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
    public ResponseEntity<?> getAllUser() {
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
    public ResponseModel updateUser(String userId, UserDto userdto) {
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

    @Override
    public ResponseEntity<?> searchUser(String value) {
        try {

            Optional<List<User>> users = userRepository
                    .searchByValue(value);
            if (users.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No matching organizations found.");
            }

            List<UserDto> userDtoList = users.get().stream()
                    .map(user -> UserDto.convertToDto(user))
                    .toList();

            return ResponseEntity.ok(userDtoList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while searching: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> updateUserStatus(String userId, Status inputStatus) {
        try {
            Optional<User> user = userRepository
                    .findByUserIdAndIsDeletedFalse(userId);

            if (user.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Organization not found");
            }

            User users = user.get();
            Status currentStatus = users.getStatus();

            if (currentStatus.equals(inputStatus)) {
                return ResponseEntity.ok("Already " + inputStatus.name() + " Refresh For Update Data");
            }

            if (currentStatus.equals(Status.ACTIVE) && inputStatus.equals(Status.INACTIVE)) {
                users.setStatus(Status.INACTIVE);
            } else if (currentStatus.equals(Status.INACTIVE) && inputStatus.equals(Status.ACTIVE)) {
                users.setStatus(Status.ACTIVE);
            } else {
                return ResponseEntity.badRequest().body("Invalid status input");
            }

            userRepository.save(users);
            return ResponseEntity.ok("Successfully Updated");


        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating status: " + ex.getMessage());

        }
    }

    @Override
    public ResponseModel login(String mobileNumber, String password) {
        Optional<User> user = userRepository.findByMobileNumberAndPasswordAndIsDeletedFalse(mobileNumber, password);

        if (user.isPresent()) {
            return ResponseModel.create(HttpStatus.OK, null, "Login successfully");
        } else {
            return ResponseModel.create(HttpStatus.NOT_FOUND, null, "Invalid phone number or password");
        }

    }

    public ResponseModel changePassword(String userId, String oldPassword, String newPassword) {
        Optional<User> users = userRepository.findByUserIdAndIsDeletedFalse(userId);

        if (users.isEmpty()) {
            return ResponseModel.create(HttpStatus.NOT_FOUND, null, "User not found");
        }
        User user = users.get();
        if (user.getPassword().equals(oldPassword)) {

            //match thay to  apde varification mate otp moklyu
            user.setPassword(newPassword);
            userRepository.save(user);
            return ResponseModel.create(HttpStatus.OK, null, "Password changed successfully");

        }

        return ResponseModel.create(HttpStatus.NOT_FOUND, null, "!  incorrect password");

    }

    public ResponseModel verifyMobileNumberForPasswordReset(String mobileNumber) {
        try {
            Optional<User> optionalUser = userRepository.findByIsDeletedAndMobileNumber(false, mobileNumber);

            if (optionalUser.isEmpty()) {
                return ResponseModel.create(
                        HttpStatus.NOT_FOUND,
                        null,
                        "No user found with the provided mobile number."
                );
            }

            return ResponseModel.create(
                    HttpStatus.OK,
                    null,
                    "Mobile number verified successfully. Proceed to OTP verification."
            );

        } catch (Exception e) {
            return ResponseModel.create(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    "An unexpected error occurred while verifying the mobile number: " + e.getMessage()
            );
        }
    }

    public ResponseModel sendOtpForPasswordReset(String mobileNumber) {
        try {
            Optional<User> optionalUser = userRepository.findByIsDeletedAndMobileNumber(false, mobileNumber);

            if (optionalUser.isEmpty()) {
                return ResponseModel.create(HttpStatus.NOT_FOUND, null, "Mobile number not found");
            }

            User user = optionalUser.get();
            String otp = String.valueOf((int) (Math.random() * 900000) + 100000);
            user.setOtp(otp);
            user.setStatus(Status.UNVERIFIED);
            userRepository.save(user);

            System.out.println("OTP sent to: " + mobileNumber + " => OTP: " + otp);

            return ResponseModel.create(HttpStatus.OK, null, "OTP sent successfully");

        } catch (Exception e) {
            return ResponseModel.create(HttpStatus.INTERNAL_SERVER_ERROR, null, "Error: " + e.getMessage());
        }
    }




}

