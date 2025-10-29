package com.example.MultiTenantTaskManagerBackend.dto;

import lombok.Data;

@Data
public class TenantSignupRequest {
    private String tenantName;
    private String adminUsername;
    private String adminPassword;
    private String adminEmail;

    // getters & setters
}