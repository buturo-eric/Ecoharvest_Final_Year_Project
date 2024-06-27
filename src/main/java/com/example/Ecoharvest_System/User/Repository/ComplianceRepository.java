package com.example.Ecoharvest_System.User.Repository;

import com.example.Ecoharvest_System.User.Model.ComplianceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ComplianceRepository extends JpaRepository<ComplianceModel, Long> {
    // You can define custom database queries here if needed
    List<ComplianceModel> findByStartDate(LocalDate startDate);
    List<ComplianceModel> findByEndDate(LocalDate endDate);
}
