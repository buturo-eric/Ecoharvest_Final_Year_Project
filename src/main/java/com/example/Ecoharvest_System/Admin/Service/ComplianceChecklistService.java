package com.example.Ecoharvest_System.Admin.Service;

import com.example.Ecoharvest_System.Admin.Model.ComplianceChecklistModel;
import com.example.Ecoharvest_System.Admin.Repository.ComplianceChecklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplianceChecklistService {

    @Autowired
    private ComplianceChecklistRepository checklistRepository;

    public List<ComplianceChecklistModel> getChecklistsByFarmId(String farmId) {
        return checklistRepository.findByFarmId(farmId);
    }
}
