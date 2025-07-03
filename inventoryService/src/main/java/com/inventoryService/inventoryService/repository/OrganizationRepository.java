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
public interface OrganizationRepository extends JpaRepository<Organization, String> {

    Optional<Organization> findByIsDeletedAndGstNoOrMobileNumber(boolean isDeleted, String gstNo, String mobileNumber);


    Optional<List<Organization>> findByIsDeletedFalse();

    Optional<Organization> findByOrganizationIdAndIsDeletedFalse(String organizationId);


    @Query("SELECT o FROM Organization o WHERE o.isDeleted = false AND " +
            "(o.organizationName LIKE %:value% OR o.gstNo LIKE %:value% OR o.mobileNumber LIKE %:value%)")
    Optional<List<Organization>> searchByKeyword(@Param("value") String value);


}
