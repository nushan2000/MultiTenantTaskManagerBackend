package com.example.MultiTenantTaskManagerBackend.controller;
import com.example.MultiTenantTaskManagerBackend.dto.TenantSignupRequest;
import com.example.MultiTenantTaskManagerBackend.dto.TenantSignupResponse;
import com.example.MultiTenantTaskManagerBackend.model.Tenant;
import com.example.MultiTenantTaskManagerBackend.service.TenantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tenants")
//@CrossOrigin(origins = "http://localhost:3000")
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @PostMapping
    public Tenant createTenant(@RequestBody Tenant tenant) {
        return tenantService.createTenant(tenant);
    }

    @GetMapping
    public List<Tenant> getAllTenants() {
        return tenantService.getAllTenants();
    }
    @PostMapping("/signup")
        public ResponseEntity<TenantSignupResponse> signup(@RequestBody TenantSignupRequest request) {
            TenantSignupResponse response = tenantService.createTenantWithAdmin(request);
            return ResponseEntity.ok(response);
        }


}

