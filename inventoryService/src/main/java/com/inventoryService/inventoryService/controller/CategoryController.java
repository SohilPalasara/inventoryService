package com.inventoryService.inventoryService.controller;

import com.inventoryService.inventoryService.dto.CategoryDto;
import com.inventoryService.inventoryService.dto.OrganizationDto;
import com.inventoryService.inventoryService.enums.Status;
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

    @GetMapping("/{id}")
    public ResponseEntity<?> getByCategoryId(@PathVariable String id) {
        return categoryServiceImpl.getByCategory(id);
    }

    @DeleteMapping("/{id}")
    public ResponseModel deleteCategory(@PathVariable String id) {

        return categoryServiceImpl.deleteCategory(id);
    }

    @PutMapping("/{id}")
    public ResponseModel updateCategory(@PathVariable String id, @RequestBody CategoryDto categoryDto) {

        return categoryServiceImpl.updateCategory(id, categoryDto);
    }

    @GetMapping("/categories")
    public ResponseEntity<?> searchCategory(@RequestParam("search") String value) {
        return categoryServiceImpl.searchCategory(value);
    }

    @PutMapping("/{id}/{status}")
    public ResponseEntity<?> updateStatus(@PathVariable String id, @PathVariable Status status) {

        return categoryServiceImpl.updateCategoryStatus(id, status);
    }

    @GetMapping("/getAllCategoryByParent")
    public ResponseEntity<?> getAllCategoryByParent() {
        return categoryServiceImpl.getAllCategoryByParent();
    }

    @GetMapping("/getByParentCategory/{id}")
    public ResponseEntity<?> getByByParentCategory(@PathVariable String id) {
        return categoryServiceImpl.getByParentCategory(id);
    }
}
