package com.example.MultiTenantTaskManagerBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantSignupResponse {
    private Long tenantId;
    private String message;

}
