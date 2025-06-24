package com.inventoryService.inventoryService.controller;

import com.inventoryService.inventoryService.dto.UserDto;
import com.inventoryService.inventoryService.service.impl.UserServiceImpl;
import com.inventoryService.inventoryService.utills.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserServiceImpl userServiceimpl;

    @PostMapping("/register")
    public ResponseModel saveUser(@RequestBody UserDto userDto) {
        return userServiceimpl.registerOrganization(userDto);
    }

}
