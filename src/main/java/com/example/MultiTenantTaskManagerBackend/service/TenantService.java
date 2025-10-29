package com.example.MultiTenantTaskManagerBackend.service;
import com.example.MultiTenantTaskManagerBackend.dto.TenantSignupRequest;
import com.example.MultiTenantTaskManagerBackend.dto.TenantSignupResponse;
import com.example.MultiTenantTaskManagerBackend.model.Tenant;
import com.example.MultiTenantTaskManagerBackend.model.User;
import com.example.MultiTenantTaskManagerBackend.repository.TenantRepository;
import com.example.MultiTenantTaskManagerBackend.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class TenantService {
    private final TenantRepository tenantRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
//    private final TenantRepository tenantRepository;

//    public TenantService(TenantRepository tenantRepository) {
//        this.tenantRepository = tenantRepository;
//    }

    public Tenant createTenant(Tenant tenant) {
        return tenantRepository.save(tenant);
    }

    public List<Tenant> getAllTenants() {
        return tenantRepository.findAll();
    }

    public TenantSignupResponse createTenantWithAdmin(TenantSignupRequest request) {
        // 1️⃣ Create tenant
        Tenant tenant = new Tenant();
        tenant.setName(request.getTenantName());
        tenant = tenantRepository.save(tenant);

        // 2️⃣ Create admin user
        User admin = new User();
        admin.setUsername(request.getAdminUsername());
        admin.setPassword(passwordEncoder.encode(request.getAdminPassword()));
        admin.setEmail(request.getAdminEmail());
        admin.setTenant(tenant);
        admin.setRole("ADMIN"); // single role now
        userRepository.save(admin);

        return new TenantSignupResponse(tenant.getId(), "Tenant created successfully");
    }

}
