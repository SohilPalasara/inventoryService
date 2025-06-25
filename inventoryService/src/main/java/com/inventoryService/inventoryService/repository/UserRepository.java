package com.inventoryService.inventoryService.repository;

import com.inventoryService.inventoryService.entity.Organization;
import com.inventoryService.inventoryService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository< User , String> {
    Optional<User> findByIsDeletedAndMobileNumber(boolean isDeleted, String mobileNumber);

    Optional<List<User>> findByIsDeletedFalse();

    Optional<User> findByUserIdAndIsDeletedFalse(String userId);
}
