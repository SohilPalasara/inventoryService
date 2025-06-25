package com.inventoryService.inventoryService.controller;

import com.inventoryService.inventoryService.dto.OrganizationDto;
import com.inventoryService.inventoryService.dto.UserDto;
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
        return userServiceimpl.registerOrganization(userDto);
    }

    @GetMapping
    public ResponseEntity<?> getAllUser() {
        return userServiceimpl.getAllUser();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getOrganizationById(@PathVariable String userId) {
        return userServiceimpl.getByUser(userId);
    }
    @DeleteMapping("/{organizationId}")
    public ResponseModel deleteOrganization(@PathVariable String organizationId) {

        return userServiceimpl.deleteUser(organizationId);
    }

    @PutMapping("/{organizationId}")
    public ResponseModel updateOrganization(@PathVariable String userId, @RequestBody UserDto userDto) {

        return userServiceimpl.updateUser(userId,userDto);
    }
}
