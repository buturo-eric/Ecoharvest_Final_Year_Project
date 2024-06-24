package com.example.Ecoharvest_System.Admin.Repository;

import com.example.Ecoharvest_System.Admin.Model.ComplianceChecklistModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplianceChecklistRepository extends JpaRepository<ComplianceChecklistModel, Long> {
    List<ComplianceChecklistModel> findByFarmId(String farmId);
}
