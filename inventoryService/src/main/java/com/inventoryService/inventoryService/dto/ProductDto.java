package com.inventoryService.inventoryService.dto;

import com.inventoryService.inventoryService.entity.Category;
import com.inventoryService.inventoryService.entity.Organization;
import com.inventoryService.inventoryService.entity.Product;
import com.inventoryService.inventoryService.enums.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    private String productId;
    private String productName;
    private String description;
    private String productCode;
    private String productImage;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Status status;
    private Category category;
    private Organization organization;

    public Product convertToEntity(){
        return Product.builder()
                .productName(this.productName)
                .description(this.description)
                .productCode(this.productCode)
                .productImage(this.productImage)
                .status(Status.ACTIVE)
                .category(this.category)
                .organization(this.organization)
                .build();
    }
    public static ProductDto convertToDto(Product product){
        return ProductDto.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .productCode(product.getProductCode())
                .productImage(product.getProductImage())
                .status(product.getStatus())
                .category(product.getCategory())
                .organization(product.getOrganization())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}
