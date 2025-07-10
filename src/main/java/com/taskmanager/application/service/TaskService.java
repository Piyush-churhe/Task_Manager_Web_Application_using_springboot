package com.taskmanager.application.service;

import com.taskmanager.application.entity.Task;
import com.taskmanager.application.entity.User;
import com.taskmanager.application.repository.TaskRepository;
import com.taskmanager.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired private UserRepository userRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Task createTask(Task task, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        task.setUser(user); // ✅ Important step
        return taskRepository.save(task);
    }


    public List<Task> getTasksByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return taskRepository.findByUser(user);
    }

    public Task updateTask(Long id, Task updatedTask) {
        return taskRepository.findById(id).map(task -> {
            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setDueDate(updatedTask.getDueDate());
            task.setPriority(updatedTask.getPriority());
            task.setStatus(updatedTask.getStatus());
            return taskRepository.save(task); // ✅ Save after updating fields
        }).orElse(null);
    }


    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
