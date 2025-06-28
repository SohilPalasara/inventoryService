package com.inventoryService.inventoryService.repository;

import com.inventoryService.inventoryService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByIsDeletedAndMobileNumber(boolean isDeleted, String mobileNumber);

    Optional<List<User>> findByIsDeletedFalse();

    Optional<User> findByUserIdAndIsDeletedFalse(String userId);

    @Query("SELECT u FROM User u WHERE  u.isDeleted = false  AND" +
            "(u.fullName LIKE %:value% OR u.mobileNumber LIKE %:value% )")
    Optional<List<User>> searchByValue(@Param("value") String value);

    Optional<User> findByMobileNumberAndPasswordAndIsDeletedFalse(String mobileNumber, String password);

}

