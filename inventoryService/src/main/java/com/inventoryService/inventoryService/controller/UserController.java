package com.inventoryService.inventoryService.controller;

import com.inventoryService.inventoryService.dto.OrganizationDto;
import com.inventoryService.inventoryService.dto.UserDto;
import com.inventoryService.inventoryService.enums.Status;
import com.inventoryService.inventoryService.service.impl.UserServiceImpl;
import com.inventoryService.inventoryService.utills.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserServiceImpl userServiceimpl;

    @PostMapping("/register")
    public ResponseModel saveUser(@RequestBody UserDto userDto) {
        return userServiceimpl.registerUser(userDto);
    }

    @GetMapping
    public ResponseEntity<?> getAllUser() {
        return userServiceimpl.getAllUser();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable String userId) {
        return userServiceimpl.getByUser(userId);
    }
    @DeleteMapping("/{userId}")
    public ResponseModel deleteUser(@PathVariable String userId) {

        return userServiceimpl.deleteUser(userId);
    }

    @PutMapping("/{userId}")
    public ResponseModel updateUser(@PathVariable String userId, @RequestBody UserDto userDto) {

        return userServiceimpl.updateUser(userId,userDto);
    }
    @GetMapping("/user")
    public ResponseEntity<?> searchUser(@RequestParam("search") String value) {

        return userServiceimpl.searchUser(value);
    }
    @PutMapping("/{userId}/{status}")
    public ResponseEntity<?> updateStatus(@PathVariable String userId, @PathVariable Status status) {

        return userServiceimpl.updateUserStatus(userId, status);
    }

    @PostMapping("/login")
    public ResponseModel login(@RequestParam("mobileNumber") String mobileNumber,
                                               @RequestParam("password") String password) {
        return userServiceimpl.login(mobileNumber,password);
    }
    @PutMapping("/{userId}/{oldPassword}/{newPassword}")
    public ResponseModel changePassword(
            @PathVariable String userId,
            @PathVariable String oldPassword,
            @PathVariable String newPassword) {
        return userServiceimpl.changePassword(userId,oldPassword,newPassword);
    }

    @GetMapping("/verify/mobile")
    public ResponseModel verifyMobileNumber(@RequestParam("verify") String mobileNumber) {
        return userServiceimpl.verifyMobileNumberForPasswordReset(mobileNumber);
    }
    @PostMapping("/send-otp/{mobileNumber}")
    public ResponseModel sendOtp(@PathVariable String mobileNumber) {
        return userServiceimpl.sendOtpForPasswordReset(mobileNumber);
    }
    @PostMapping("/reset-password")
    public ResponseModel resetPassword(
            @RequestParam("mobileNumber") String mobileNumber,
            @RequestParam("otp") String otp,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword) {

        return userServiceimpl.resetPasswordUsingOtp(mobileNumber, otp, newPassword, confirmPassword);
    }




}
