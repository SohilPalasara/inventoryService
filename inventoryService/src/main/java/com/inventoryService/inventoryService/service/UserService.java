package com.inventoryService.inventoryService.service;

import com.inventoryService.inventoryService.dto.UserDto;
import com.inventoryService.inventoryService.utills.ResponseModel;
import org.springframework.http.ResponseEntity;

public interface UserService {
    public ResponseModel registerOrganization(UserDto userDto) ;
    public ResponseEntity getAllUser();
    public ResponseEntity<?> getByUser(String userId);
    public ResponseModel deleteUser(String organizationId);
    public ResponseModel updateUser(String userId, UserDto userDto);
}
