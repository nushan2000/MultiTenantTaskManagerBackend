package com.example.MultiTenantTaskManagerBackend.repository;


import com.example.MultiTenantTaskManagerBackend.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {
}

