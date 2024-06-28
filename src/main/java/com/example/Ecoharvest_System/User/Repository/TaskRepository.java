package com.example.Ecoharvest_System.User.Repository;

import com.example.Ecoharvest_System.User.Model.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskModel, Long> {
    // Custom database queries can be defined here if needed
    long countByStatus(String status);
    long countByOccurrence(String status);
    // Method to count tasks grouped by compliance
    @Query("SELECT t.compliance.complianceName, COUNT(t) FROM TaskModel t GROUP BY t.compliance.complianceName")
    List<Object[]> countTasksByCompliance();
    List<TaskModel> findByComplianceId(Long complianceId);
}

