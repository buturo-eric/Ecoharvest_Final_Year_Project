package com.example.Ecoharvest_System.Admin.Controllers;

import com.example.Ecoharvest_System.Admin.Model.ComplianceStatusModel;
import com.example.Ecoharvest_System.Admin.Service.ComplianceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Controller
public class ComplianceDashboardController {
    @Autowired
    private ComplianceService complianceService;

    @GetMapping("/complianceDashboard")
    public String complianceDashboard(Model model, @RequestParam(required = false) String farmId) {
        List<ComplianceStatusModel> complianceStatuses = (farmId != null) ?
                complianceService.getComplianceStatusByFarm(farmId) :
                complianceService.getAllComplianceStatuses();
        model.addAttribute("complianceStatus", complianceStatuses);
        return "Admin/complianceDashboard";  // Name of the Thymeleaf template
    }
}

