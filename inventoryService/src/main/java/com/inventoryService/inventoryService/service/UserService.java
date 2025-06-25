package com.inventoryService.inventoryService.service;

import com.inventoryService.inventoryService.dto.UserDto;
import com.inventoryService.inventoryService.utills.ResponseModel;

public interface UserService {
    public ResponseModel registerOrganization(UserDto userDto) ;
}
