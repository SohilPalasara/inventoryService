package com.inventoryService.inventoryService.repository;

import com.inventoryService.inventoryService.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization , String> {

    Optional<Organization> findByIsDeletedAndGstNoOrMobileNumber( boolean isDeleted,String gstNo, String mobileNumber);

  


List<Organization>findByIsDeletedFalse();

   Optional< Organization> findByOrganizationIdAndIsDeletedFalse(String organizationId);


    Optional<Organization> findByOrganizationIdAndIsDeletedTrue(String organizationId);
}
