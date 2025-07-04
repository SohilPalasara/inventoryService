package com.inventoryService.inventoryService.repository;

import com.inventoryService.inventoryService.entity.Category;
import com.inventoryService.inventoryService.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category , String> {
    Optional<Category> findByIdAndIsDeletedFalse(String organizationId);

    Optional<Category> findByIsDeletedAndCategoryName(boolean b, String categoryName);

@Query("select c from Category c where c.isDeleted=false And c.parentCategory Is Null ")
Optional<List<Category>> findOnlyParentCategories();

@Query("select c from Category c  where c.isDeleted=false And c.categoryName Like%:value%")
    Optional<List<Category>> searchByKeyword(@Param("value") String value);

    Optional<List<Category>> findByIsDeletedFalse();

    Optional<List<Category>> findByParentCategoryIdAndIsDeletedFalse(String id);
}
