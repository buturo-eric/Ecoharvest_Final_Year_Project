package com.example.Ecoharvest_System.Admin.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Ecoharvest_System.Admin.Model.ComplianceStatusModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplianceStatusRepository extends JpaRepository<ComplianceStatusModel, Long> {
    List<ComplianceStatusModel> findByFarmId(String farmId);
    List<ComplianceStatusModel> findAll();

}
