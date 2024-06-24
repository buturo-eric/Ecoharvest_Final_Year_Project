package com.example.Ecoharvest_System.Admin.Service;

import com.example.Ecoharvest_System.Admin.Model.ComplianceStatusModel;
import com.example.Ecoharvest_System.Admin.Repository.ComplianceStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ComplianceService {
    @Autowired
    private ComplianceStatusRepository complianceStatusRepository;

    public List<ComplianceStatusModel> getComplianceStatusByFarm(String farmId) {
        return complianceStatusRepository.findByFarmId(farmId);
    }

    public List<ComplianceStatusModel> getAllComplianceStatuses() {
        return complianceStatusRepository.findAll();
    }
}
