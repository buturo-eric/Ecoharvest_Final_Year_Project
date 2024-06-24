package com.example.Ecoharvest_System.Admin.Controllers;

import com.example.Ecoharvest_System.Admin.Service.ComplianceChecklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ComplianceChecklistController {

    @Autowired
    private ComplianceChecklistService checklistService;

    @GetMapping("/User/checklist")
    public String checklist(Model model, @RequestParam String farmId) {
        model.addAttribute("checklists", checklistService.getChecklistsByFarmId(farmId));
        return "User/checklist";  // Thymeleaf template for the farmer to view and interact with the checklist
    }
}
