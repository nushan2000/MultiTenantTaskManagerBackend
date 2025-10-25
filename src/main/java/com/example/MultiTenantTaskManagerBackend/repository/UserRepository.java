package com.example.MultiTenantTaskManagerBackend.repository;

import com.example.MultiTenantTaskManagerBackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 🔹 For authentication (login)
    Optional<User> findByUsername(String username);

    // 🔹 For multi-tenant operations
    List<User> findByTenantId(Long tenantId);
}
