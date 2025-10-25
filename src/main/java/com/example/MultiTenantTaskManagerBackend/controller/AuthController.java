package com.example.MultiTenantTaskManagerBackend.controller;

import com.example.MultiTenantTaskManagerBackend.model.AuthRequest;
import com.example.MultiTenantTaskManagerBackend.model.User;
import com.example.MultiTenantTaskManagerBackend.repository.UserRepository;
import com.example.MultiTenantTaskManagerBackend.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        // 1️⃣ Find user by username
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2️⃣ Check password (for simplicity, plain text here; in real apps, hash it)
        if (!user.getPassword().equals(request.getPassword())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        // 3️⃣ Generate JWT token including tenantId
        String token = jwtUtil.generateToken(user.getUsername(), user.getTenantId());

        // 4️⃣ Return token in response
        return ResponseEntity.ok(Map.of("token", token));
    }

    // DTO for login request

}
