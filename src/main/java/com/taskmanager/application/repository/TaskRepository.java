package com.taskmanager.application.repository;

import com.taskmanager.application.entity.Task;
import com.taskmanager.application.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);
}
