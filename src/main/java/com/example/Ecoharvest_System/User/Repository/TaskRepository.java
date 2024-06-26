package com.example.Ecoharvest_System.User.Repository;

import com.example.Ecoharvest_System.User.Model.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskModel, Long> {
    // Custom database queries can be defined here if needed
}
