package com.inventoryService.inventoryService.service.impl;

import com.inventoryService.inventoryService.dto.CategoryDto;
import com.inventoryService.inventoryService.dto.OrganizationDto;
import com.inventoryService.inventoryService.entity.Category;
import com.inventoryService.inventoryService.entity.Organization;
import com.inventoryService.inventoryService.repository.CategoryRepository;
import com.inventoryService.inventoryService.repository.OrganizationRepository;
import com.inventoryService.inventoryService.service.CategoryService;
import com.inventoryService.inventoryService.utills.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Override
    public ResponseModel addCategory(CategoryDto categoryDto) {
        try {
            Optional<Organization> exist =categoryRepository.findByIsDeletedAndCategoryName(
                    false,
                    categoryDto.getCategoryName()
            );
            if (exist.isPresent()) {
                return ResponseModel.create(
                        HttpStatus.FOUND,
                        null,
                        "Category already exists with this Category name"
                );
            }
            Optional<Organization> organization = organizationRepository.findByOrganizationIdAndIsDeletedFalse(categoryDto.getOrganizationId());
            if (organization.isEmpty()) {
                return ResponseModel.create(
                        HttpStatus.NOT_FOUND,
                        null,
                        "Organization not found"
                );
            }

            if(categoryDto.getParentCategoryId()!=null){
            Optional<Category> optionalCategory = categoryRepository.findByIdAndIsDeletedFalse(categoryDto.getParentCategoryId());
            if (optionalCategory.isEmpty()) {
                return ResponseModel.create(
                        HttpStatus.NOT_FOUND,
                        null,
                        "category not found"
                );
            }
                Category category = categoryDto.convertToEntity(optionalCategory.get(),organization.get());
                categoryRepository.save(category);
                return ResponseModel.create(
                        HttpStatus.OK,
                        CategoryDto.convertToDto(category),
                        "Organization saved successfully"
                );
}
            else {
                Category category = categoryDto.convertToEntity(null, organization.get());
                categoryRepository.save(category);
                return ResponseModel.create(
                        HttpStatus.OK,
                        CategoryDto.convertToDto(category),
                        "Root category saved successfully"
                );
            }

        } catch (Exception e) {

            return ResponseModel.create(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null, "An error occurred sir: "
            );
        }
    }
}
