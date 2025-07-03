package com.inventoryService.inventoryService.service;

import com.inventoryService.inventoryService.dto.CategoryDto;
import com.inventoryService.inventoryService.utills.ResponseModel;
import org.springframework.http.ResponseEntity;

public interface CategoryService {
    public ResponseModel addCategory(CategoryDto categoryDto);
    public ResponseEntity<?> getAllCategory();
}
