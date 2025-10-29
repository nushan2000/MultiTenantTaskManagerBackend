package com.example.MultiTenantTaskManagerBackend.controller;

import com.example.MultiTenantTaskManagerBackend.model.Task;
import com.example.MultiTenantTaskManagerBackend.repository.TaskRepository;
import com.example.MultiTenantTaskManagerBackend.security.JwtUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
//@CrossOrigin(origins = "http://localhost:3000")
public class TaskController {

    private final TaskRepository taskRepository;
    private final JwtUtil jwtUtil;

    public TaskController(TaskRepository taskRepository, JwtUtil jwtUtil) {
        this.taskRepository = taskRepository;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public List<Task> getTasks(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7); // remove "Bearer "
        Long tenantId = jwtUtil.extractTenantId(token); // ✅ extract tenant from JWT
        return taskRepository.findByTenantId(tenantId);
    }

    @PostMapping
    public Task createTask(@RequestHeader("Authorization") String authHeader,
                           @RequestBody Task task) {
        String token = authHeader.substring(7);
        Long tenantId = jwtUtil.extractTenantId(token);
        task.setTenantId(tenantId);
        return taskRepository.save(task);
    }
}
