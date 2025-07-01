package com.inventoryService.inventoryService.service;

import com.inventoryService.inventoryService.dto.UserDto;
import com.inventoryService.inventoryService.enums.Status;
import com.inventoryService.inventoryService.utills.ResponseModel;
import org.springframework.http.ResponseEntity;

public interface UserService {
    public ResponseModel registerUser(UserDto userDto) ;
    public ResponseEntity getAllUser();
    public ResponseEntity<?> getByUser(String userId);
    public ResponseModel deleteUser(String organizationId);
    public ResponseModel updateUser(String userId, UserDto userDto);
    public ResponseEntity<?> searchUser(String value) ;
    public ResponseEntity<?> updateUserStatus(String organizationId, Status inputStatus);
    public ResponseModel login(String mobileNumber, String password);
    public ResponseModel changePassword(String userId, String oldPassword, String newPassword);
    public ResponseModel verifyMobileNumberForPasswordReset(String mobileNumber);
    public ResponseModel sendOtpForPasswordReset(String mobileNumber);
    public ResponseModel resetPasswordUsingOtp(String mobileNumber , String otp ,String newPassword ,String confirmPassword) ;
    }
