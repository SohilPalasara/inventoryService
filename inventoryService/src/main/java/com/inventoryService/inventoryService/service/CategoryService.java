package com.inventoryService.inventoryService.service;

import com.inventoryService.inventoryService.dto.CategoryDto;
import com.inventoryService.inventoryService.utills.ResponseModel;

public interface CategoryService {
    public ResponseModel addCategory(CategoryDto categoryDto);
}
