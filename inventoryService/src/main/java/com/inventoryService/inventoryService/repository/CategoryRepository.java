package com.inventoryService.inventoryService.repository;

import com.inventoryService.inventoryService.entity.Category;
import com.inventoryService.inventoryService.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category , String> {
    Optional<Category> findByIdAndIsDeletedFalse(String organizationId);

    Optional<Organization> findByIsDeletedAndCategoryName(boolean b, String categoryName);
}
