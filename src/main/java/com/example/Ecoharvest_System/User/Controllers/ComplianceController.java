package com.example.Ecoharvest_System.User.Controllers;

import com.example.Ecoharvest_System.User.Model.ComplianceModel;
import com.example.Ecoharvest_System.User.Service.ComplianceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import java.io.IOException;

@Controller
public class ComplianceController {

    @Autowired
    private ComplianceService complianceService;

    // Get all compliances
    @GetMapping("/compliances")
    public String listCompliances(Model model) {
        List<ComplianceModel> compliances = complianceService.findAllCompliances();
        model.addAttribute("compliances", compliances);
        return "Admin/complianceList";  // The Thymeleaf template to display the table
    }

    // Display the compliance creation form
    @GetMapping("/createCompliance")
    public String createComplianceForm(Model model) {
        model.addAttribute("compliance", new ComplianceModel());
        return "Admin/createCompliance";  // Ensure this matches your Thymeleaf template name
    }

    // Process the form submission
    @PostMapping("/compliance/save")
    public String saveCompliance(
            @ModelAttribute("compliance") ComplianceModel compliance,
            @RequestParam("documentFile") MultipartFile file, // This should match the file input name in the form
            RedirectAttributes redirectAttributes) {

        if (!file.isEmpty()) {
            try {
                // This service method should save the file and return the URL or file path
                String docUrl = complianceService.saveDocument(file);
                compliance.setComplianceDocument(docUrl);  // Set the file URL/path
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("message", "File upload failed: " + e.getMessage());
                return "redirect:/createCompliance";
            }
        }

        try {
            complianceService.createCompliance(compliance);
            redirectAttributes.addFlashAttribute("message", "Compliance successfully created!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error saving compliance: " + e.getMessage());
        }

        return "redirect:/createCompliance";  // Redirect to prevent duplicate submissions
    }

    // Edit compliance form
    @GetMapping("/editCompliance/{id}")
    public String editComplianceForm(@PathVariable Long id, Model model) {
        ComplianceModel compliance = complianceService.findById(id);
        model.addAttribute("compliance", compliance);
        return "Admin/editCompliance";  // The Thymeleaf template for the edit form
    }

    // Save edited compliance
    @PostMapping("/updateCompliance")
    public String updateCompliance(@ModelAttribute("compliance") ComplianceModel compliance, RedirectAttributes redirectAttributes) {
        complianceService.createCompliance(compliance);  // Reusing the creation logic for updates
        redirectAttributes.addFlashAttribute("message", "Compliance updated successfully!");
        return "redirect:/compliances";
    }

    // Delete compliance
    @GetMapping("/deleteCompliance/{id}")
    public String deleteCompliance(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            complianceService.deleteCompliance(id);
            redirectAttributes.addFlashAttribute("message", "Compliance deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error deleting compliance: " + e.getMessage());
        }
        return "redirect:/compliances";
    }


}

