package com.inventoryService.inventoryService.repository;

import com.inventoryService.inventoryService.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization , String> {




    Organization findByIsDeletedAndGstNoOrMobileNumber( boolean isDeleted,String gstNo, String mobileNumber);

}
