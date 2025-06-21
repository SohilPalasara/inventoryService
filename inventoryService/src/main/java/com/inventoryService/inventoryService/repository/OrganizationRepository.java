package com.inventoryService.inventoryService.repository;

import com.inventoryService.inventoryService.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization , String> {

    Optional<Organization> findByIsDeletedAndGstNoOrMobileNumber( boolean isDeleted,String gstNo, String mobileNumber);

  


Optional<List<Organization>>findByIsDeletedFalse();

   Optional< Organization> findByOrganizationIdAndIsDeletedFalse(String organizationId);


    Optional<Organization> findByOrganizationIdAndIsDeletedTrue(String organizationId);
    Optional<List<Organization>> findByIsDeletedFalseAndOrganizationNameContainingIgnoreCaseOrGstNoContainingIgnoreCaseOrMobileNumberContainingIgnoreCase(
            String name, String gst, String mobile
    );
   // @Query(nativeQuery = false , value = "select name from organization where name = ?1" )
//   @Query(value = "SELECT * FROM organization WHERE is_deleted = false AND LOWER(organization_name) LIKE LOWER(CONCAT('%', :key, '%'))", nativeQuery = true)
//    List<Organization> findByIsDeletedFalseAndOrganizationNameContainingIgnoreCase(@Param("key") String name);
    @Query(value = """
    SELECT * FROM organization 
    WHERE is_deleted = false 
    AND LOWER(organization_name) LIKE LOWER(CONCAT('%', :key, '%'))
""", nativeQuery = true)
    List<Organization> findByIsDeletedFalseAndOrganizationNameContainingIgnoreCase(@Param("key") String name);

    List<Organization> findByIsDeletedFalseAndGstNoContainingIgnoreCase(String gstNo);
    List<Organization> findByIsDeletedFalseAndMobileNumberContainingIgnoreCase(String mobileNumber);



}
