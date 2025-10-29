package com.example.MultiTenantTaskManagerBackend.controller;

import com.example.MultiTenantTaskManagerBackend.model.AuthRequest;
import com.example.MultiTenantTaskManagerBackend.model.User;
import com.example.MultiTenantTaskManagerBackend.repository.UserRepository;
import com.example.MultiTenantTaskManagerBackend.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getUsername(), user.getTenant().getId(), user.getRole());
        return ResponseEntity.ok(Map.of("token", token));
    }


    // DTO for login request

}
