package com.example.MultiTenantTaskManagerBackend.controller;

import com.example.MultiTenantTaskManagerBackend.model.Tenant;
import com.example.MultiTenantTaskManagerBackend.model.User;
import com.example.MultiTenantTaskManagerBackend.repository.TenantRepository;
import com.example.MultiTenantTaskManagerBackend.service.UserService;
import com.example.MultiTenantTaskManagerBackend.tenant.TenantContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
//@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final TenantRepository tenantRepository;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;





    @PostMapping
    @PreAuthorize("hasRole('ADMIN')") // 👈 only admin can create
    public User createUser(@RequestBody User user) {
        Long tenantId = Long.parseLong(TenantContext.getCurrentTenant());
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new RuntimeException("Tenant not found"));

        user.setTenant(tenant);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER"); // new users are regular users
        return userService.createUser(user);
    }



    @GetMapping
    public List<User> getUsers() {
        Long tenantId = Long.parseLong(TenantContext.getCurrentTenant());
        return userService.getUsersByTenant(tenantId);
    }
}
