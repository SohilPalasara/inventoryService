package com.inventoryService.inventoryService.service;

import com.inventoryService.inventoryService.dto.CategoryDto;
import com.inventoryService.inventoryService.enums.Status;
import com.inventoryService.inventoryService.utills.ResponseModel;
import org.springframework.http.ResponseEntity;

public interface CategoryService {
    public ResponseModel addCategory(CategoryDto categoryDto);
    public ResponseEntity<?> getAllCategory();
    public ResponseEntity<?> getByCategory(String id);
    public ResponseModel deleteCategory(String id);
    public ResponseModel updateCategory(String id, CategoryDto categoryDto);
    public ResponseEntity<?> searchCategory(String value);
    public ResponseEntity<?> updateCategoryStatus(String id, Status inputStatus);
    public ResponseEntity<?> getAllCategoryByParent();
    public ResponseEntity<?> getByParentCategory(String Id);
}
