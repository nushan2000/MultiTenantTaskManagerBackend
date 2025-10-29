package com.example.MultiTenantTaskManagerBackend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;
    private String role;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;


}
