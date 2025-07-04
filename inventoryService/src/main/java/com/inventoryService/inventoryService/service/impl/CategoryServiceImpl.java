package com.inventoryService.inventoryService.service.impl;

import com.inventoryService.inventoryService.dto.CategoryDto;
import com.inventoryService.inventoryService.entity.Category;
import com.inventoryService.inventoryService.entity.Organization;
import com.inventoryService.inventoryService.enums.Status;
import com.inventoryService.inventoryService.repository.CategoryRepository;
import com.inventoryService.inventoryService.repository.OrganizationRepository;
import com.inventoryService.inventoryService.service.CategoryService;
import com.inventoryService.inventoryService.utills.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
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
            Optional<Category> exist = categoryRepository.findByIsDeletedAndCategoryName(
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
                        "Category not found"
                );
            }
            Category category;

            if (categoryDto.getParentCategoryId() != null) {
                Optional<Category> parentCategory = categoryRepository.findByIdAndIsDeletedFalse(categoryDto.getParentCategoryId());
                if (parentCategory.isEmpty()) {
                    return ResponseModel.create(
                            HttpStatus.NOT_FOUND,
                            null,
                            "Parent Category not found"
                    );
                }

                category = categoryDto.convertToEntity(parentCategory.get(), organization.get());

            } else {
                category = categoryDto.convertToEntity(null, organization.get());

            }
            categoryRepository.save(category);
            return ResponseModel.create(
                    HttpStatus.OK,
                    CategoryDto.convertToDto(category),
                    "Category saved successfully"
            );

        } catch (Exception e) {

            return ResponseModel.create(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null, "An error occurred sir: "
            );
        }
    }

    @Override
    public ResponseEntity<?> getAllCategory() {
        try {
            Optional<List<Category>> categories = categoryRepository.findByIsDeletedFalse();

            if (categories.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Category Found");
            }

            List<CategoryDto> categoryDtoList = categories.get().stream()
                    .map(category -> CategoryDto.convertToDto(category))
                    .toList();

            return ResponseEntity.ok(categoryDtoList);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getByCategory(String Id) {
        try {
            Optional<Category> categoryOptional = categoryRepository.findByIdAndIsDeletedFalse(Id);

            if (categoryOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No categoryId found");
            }

            CategoryDto categoryDto = CategoryDto.convertToDto(categoryOptional.get());

            return ResponseEntity.ok(categoryDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    @Override
    public ResponseModel deleteCategory(String Id) {
        try {
            Optional<Category> optionalCategory = categoryRepository.findByIdAndIsDeletedFalse(Id);
            if (optionalCategory.isEmpty()) {
                return ResponseModel.create(
                        HttpStatus.NOT_FOUND,
                        null,
                        "category not found  or deleted"
                );
            }


            Category category = optionalCategory.get();
            category.setDeleted(true);
            categoryRepository.save(category);
            return ResponseModel.create(
                    HttpStatus.OK,
                    CategoryDto.convertToDto(category),
                    "Category deleted successfully"
            );
        } catch (Exception e) {
            return ResponseModel.create(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null,
                    "An error occurred while deleting: " + e.getMessage()
            );

        }
    }

    @Override
    public ResponseModel updateCategory(String id, CategoryDto categoryDto) {
        try {
            Optional<Category> optionalCategory = categoryRepository.findByIdAndIsDeletedFalse(id);

            if (optionalCategory.isEmpty()) {
                return ResponseModel.create(HttpStatus.NOT_FOUND, null, "Category not found ");
            }


            categoryDto.updateEntity(optionalCategory.get());

            Category updated = categoryRepository.save(optionalCategory.get());

            return ResponseModel.create(
                    HttpStatus.OK,
                    CategoryDto.convertToDto(updated),
                    "category updated successfully"
            );

        } catch (Exception e) {
            return ResponseModel.create(HttpStatus.INTERNAL_SERVER_ERROR, null, "Update failed: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> searchCategory(String value) {
        try {

            Optional<List<Category>> categories = categoryRepository
                    .searchByKeyword(value);
            if (categories.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No matching organizations found.");
            }

            List<CategoryDto> categoryDtoList = categories.get().stream()
                    .map(category -> CategoryDto.convertToDto(category))
                    .toList();

            return ResponseEntity.ok(categoryDtoList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while searching: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> updateCategoryStatus(String id, Status inputStatus) {
        try {
            Optional<Category> optionalCategory = categoryRepository
                    .findByIdAndIsDeletedFalse(id);

            if (optionalCategory.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
            }

            Category category = optionalCategory.get();
            Status currentStatus = category.getStatus();

            if (currentStatus.equals(inputStatus)) {
                return ResponseEntity.ok("Already " + inputStatus.name() + " Refresh For Update Data");
            }

            if (currentStatus.equals(Status.ACTIVE) && inputStatus.equals(Status.INACTIVE)) {
                category.setStatus(Status.INACTIVE);
            } else if (currentStatus.equals(Status.INACTIVE) && inputStatus.equals(Status.ACTIVE)) {
                category.setStatus(Status.ACTIVE);
            } else {
                return ResponseEntity.badRequest().body("Invalid status input");
            }

            categoryRepository.save(category);
            return ResponseEntity.ok("Successfully Updated");


        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating status: " + ex.getMessage());

        }
    }

    @Override
    public ResponseEntity<?> getAllCategoryByParent() {
        try {
            Optional<List<Category>> categories = categoryRepository.findOnlyParentCategories();

            if (categories.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Category Found");
            }
            List<CategoryDto> categoryDtoList = categories.get().stream()
                    .map(category -> CategoryDto.convertToDto(category))
                    .toList();

            return ResponseEntity.ok(categoryDtoList);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }
}
