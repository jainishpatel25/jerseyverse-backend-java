package com.ecommerce.jerseyverse.repository;

import com.ecommerce.jerseyverse.entity.User;
import com.ecommerce.jerseyverse.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);


    @Query("""
        SELECT u
        FROM User u
        WHERE u.role = :role
          AND (
              LOWER(u.name) LIKE LOWER(CONCAT('%', :search, '%'))
              OR LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%'))
          )
        """)
    Page<User> findByRoleAndSearch(
            @Param("role") Role role,
            @Param("search") String search,
            Pageable pageable
    );

}