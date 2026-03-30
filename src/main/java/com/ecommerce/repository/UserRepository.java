package com.ecommerce.repository;

import com.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndIsDeletedFalse(String email);

    // Hard Delete Query
    void deleteByIsDeletedTrueAndDeletedAtBefore(LocalDateTime thresholdDate);
}