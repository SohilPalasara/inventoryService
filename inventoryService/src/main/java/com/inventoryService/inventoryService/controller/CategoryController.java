package com.inventoryService.inventoryService.controller;

import com.inventoryService.inventoryService.dto.CategoryDto;
import com.inventoryService.inventoryService.dto.OrganizationDto;
import com.inventoryService.inventoryService.service.impl.CategoryServiceImpl;
import com.inventoryService.inventoryService.utills.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryServiceImpl;
    @PostMapping
    public ResponseModel addCategory(@RequestBody CategoryDto categoryDto) {
        return categoryServiceImpl.addCategory(categoryDto);
    }
    @GetMapping
    public ResponseEntity<?> getAllCategory() {
        return categoryServiceImpl.getAllCategory();
    }

    @GetMapping("/{Id}")
    public ResponseEntity<?> getByCategoryId(@PathVariable String Id) {
        return categoryServiceImpl.getByCategory(Id);
    }
    @DeleteMapping("/{Id}")
    public ResponseModel deleteCategory(@PathVariable String Id) {

        return categoryServiceImpl.deleteCategory(Id);
    }
}
