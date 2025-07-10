package com.taskmanager.application.controller;

import com.taskmanager.application.dto.AssignTaskRequest;
import com.taskmanager.application.entity.Task;
import com.taskmanager.application.entity.User;
import com.taskmanager.application.repository.TaskRepository;
import com.taskmanager.application.repository.UserRepository;
import com.taskmanager.application.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired private UserRepository userRepo;
    @Autowired private TaskRepository taskRepo;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepo.findAll()
                .stream()
                .filter(u -> !u.getRole().equalsIgnoreCase("ADMIN"))
                .toList();
    }


    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/tasks")
    public List<Task> getAllTasks() {
        return taskRepo.findAll();
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        taskRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/assign-task")
    public ResponseEntity<Task> assignTaskToUser(@RequestBody AssignTaskRequest request) {
        User user = userRepo.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(LocalDate.parse(request.getDueDate()));
        task.setTime(LocalTime.parse(request.getTime()));
        task.setPriority(request.getPriority());
        task.setStatus(request.getStatus());
        task.setUser(user);

        Task saved = taskRepo.save(task);
        return ResponseEntity.ok(saved);
    }


}
