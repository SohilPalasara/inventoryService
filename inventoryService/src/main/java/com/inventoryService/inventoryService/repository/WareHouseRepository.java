package com.inventoryService.inventoryService.repository;

import com.inventoryService.inventoryService.entity.WareHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WareHouseRepository extends JpaRepository<WareHouse, String> {
    Optional<List<WareHouse>> findByIsDeletedFalse();

    Optional<WareHouse> findByWareHouseIdAndIsDeletedFalse(String wareHouseId);

    @Query("select w from WareHouse w where w.isDeleted = false AND" +
            "(w.locationOrArea LIKE %:value%  or w.title LIKE %:value%)")
   // @Query("SELECT w FROM WareHouse w WHERE w.isDeleted = false AND (w.locationOrArea LIKE %:value% OR w.title LIKE %:value%)")

    Optional<List<WareHouse>> searchByValue(String value);
}
