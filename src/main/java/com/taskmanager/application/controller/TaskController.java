package com.taskmanager.application.controller;

import com.taskmanager.application.entity.Task;
import com.taskmanager.application.entity.User;
import com.taskmanager.application.repository.TaskRepository;
import com.taskmanager.application.repository.UserRepository;
import com.taskmanager.application.service.TaskService;
import com.taskmanager.application.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepo;
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private JwtUtil jwtUtil;

    private String getUsernameFromToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return jwtUtil.extractUsername(authHeader.substring(7));
        }
        return null;
    }

    @GetMapping
    public List<Task> getUserTasks(Authentication authentication) {
        String username = authentication.getName();
        return taskService.getTasksByUsername(username);
    }

    @PostMapping
    public ResponseEntity<Task> addTask(@RequestBody Task task, Authentication authentication) {
        String username = authentication.getName(); // comes from JWT
        Task saved = taskService.createTask(task, username);
        return ResponseEntity.ok(saved);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask, Authentication auth) {
        String username = auth.getName();
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = taskRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!task.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setTime(updatedTask.getTime());
        task.setDueDate(updatedTask.getDueDate());
        task.setPriority(updatedTask.getPriority());
        task.setStatus(updatedTask.getStatus());

        taskRepo.save(task);
        return ResponseEntity.ok(task);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = taskRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!task.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        taskRepo.delete(task);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id, Authentication auth) {
        String username = auth.getName();
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = taskRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!task.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(task);
    }


}
