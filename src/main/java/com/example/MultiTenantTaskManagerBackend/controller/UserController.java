package com.example.MultiTenantTaskManagerBackend.controller;

import com.example.MultiTenantTaskManagerBackend.model.User;
import com.example.MultiTenantTaskManagerBackend.service.UserService;
import com.example.MultiTenantTaskManagerBackend.tenant.TenantContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        user.setTenantId(Long.parseLong(TenantContext.getCurrentTenant()));
        return userService.createUser(user);
    }

    @GetMapping
    public List<User> getUsers() {
        Long tenantId = Long.parseLong(TenantContext.getCurrentTenant());
        return userService.getUsersByTenant(tenantId);
    }
}
