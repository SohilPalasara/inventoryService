package com.inventoryService.inventoryService.dto;

import com.inventoryService.inventoryService.entity.Category;
import com.inventoryService.inventoryService.entity.Organization;
import com.inventoryService.inventoryService.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {
    private String id;
    private String categoryName;
    private String description;
    private  boolean isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Status status;
    private String parentCategoryId;
    private String organizationId;

    public Category convertToEntity( Category parentCategory, Organization organization) {
        return Category.builder()
                .categoryName(this.categoryName)
                .description(this.description)
                .status(Status.ACTIVE)
                .parentCategory(parentCategory)
                .organization(organization)
                .build();
    }
    public static  CategoryDto convertToDto(Category category){
       return  CategoryDto.builder()
               .id(category.getId())
               .categoryName(category.getCategoryName())
               .description(category.getDescription())
               .isDeleted(category.isDeleted())
               .status(category.getStatus())
               .createdAt(category.getCreatedAt())
               .updatedAt(category.getUpdatedAt())
               .organizationId(category.getOrganization() != null ? category.getOrganization().getOrganizationId() : null)
               .parentCategoryId(category.getParentCategory() != null ? category.getParentCategory().getId() : null)


               .build();
    }


}
