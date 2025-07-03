package com.inventoryService.inventoryService.controller;

import com.inventoryService.inventoryService.dto.CategoryDto;
import com.inventoryService.inventoryService.dto.OrganizationDto;
import com.inventoryService.inventoryService.service.impl.CategoryServiceImpl;
import com.inventoryService.inventoryService.utills.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryServiceImpl;
    @PostMapping
    public ResponseModel addCategory(@RequestBody CategoryDto categoryDto) {
        return categoryServiceImpl.addCategory(categoryDto);
    }
}
