package com.example.Ecoharvest_System.Admin.Controllers;

import com.example.Ecoharvest_System.Admin.Model.CommunityEducationModel;
import com.example.Ecoharvest_System.Admin.Service.CommunityEducationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class CommunityEducationController {

    @Autowired
    private CommunityEducationService communityEducationService;

    @GetMapping("/communityEducation")
    public String getAllCommunityEducation(Model model) {
        List<CommunityEducationModel> allEntries = communityEducationService.getAllCommunityEducations();
        List<CommunityEducationModel> todayEntries = allEntries.stream()
                .filter(entry -> entry.getDate().equals(LocalDate.now()))
                .collect(Collectors.toList());
        List<CommunityEducationModel> otherEntries = allEntries.stream()
                .filter(entry -> !entry.getDate().equals(LocalDate.now()))
                .collect(Collectors.toList());

        // Convert entries to JSON for FullCalendar
        List<Map<String, Object>> events = new ArrayList<>();
        for (CommunityEducationModel entry : todayEntries) {
            Map<String, Object> event = new HashMap<>();
            event.put("title", "Today's Meeting");
            event.put("start", entry.getDate().toString());
            event.put("url", entry.getLink());
            event.put("description", entry.getDescription());
            events.add(event);
        }
        for (CommunityEducationModel entry : otherEntries) {
            Map<String, Object> event = new HashMap<>();
            event.put("title", "Upcoming Meeting");
            event.put("start", entry.getDate().toString());
            event.put("url", entry.getLink());
            event.put("description", entry.getDescription());
            events.add(event);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String eventsJson = "";
        try {
            eventsJson = objectMapper.writeValueAsString(events);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        model.addAttribute("todayEntries", todayEntries);
        model.addAttribute("otherEntries", otherEntries);
        model.addAttribute("eventsJson", eventsJson);

        return "User/CommunityEducation";
    }


    @GetMapping("/communityEducationList")
    public String listCommunityEducations(Model model) {
        List<CommunityEducationModel> communityEducations = communityEducationService.getAllCommunityEducations();
        model.addAttribute("communityEducations", communityEducations);
        return "Admin/communityEducationList";
    }

    @GetMapping("/createCommunityEducation")
    public String createCommunityEducationForm(Model model) {
        model.addAttribute("communityEducation", new CommunityEducationModel());
        return "Admin/createCommunityEducation";
    }

    @PostMapping("/communityEducation/save")
    public String saveCommunityEducation(@ModelAttribute("communityEducation") CommunityEducationModel communityEducation,
                                         RedirectAttributes redirectAttributes) {
        try {
            communityEducationService.saveCommunityEducation(communityEducation);
            redirectAttributes.addFlashAttribute("message", "Community Education successfully created!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error saving Community Education: " + e.getMessage());
        }
        return "redirect:/communityEducationList";
    }

    @GetMapping("/editCommunityEducation/{id}")
    public String editCommunityEducationForm(@PathVariable Long id, Model model) {
        CommunityEducationModel communityEducation = communityEducationService.getCommunityEducationById(id);
        model.addAttribute("communityEducation", communityEducation);
        return "Admin/editCommunityEducation";
    }

    @PostMapping("/communityEducation/update")
    public String updateCommunityEducation(@ModelAttribute("communityEducation") CommunityEducationModel communityEducation,
                                           RedirectAttributes redirectAttributes) {
        try {
            communityEducationService.saveCommunityEducation(communityEducation);
            redirectAttributes.addFlashAttribute("message", "Community Education successfully updated!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error updating Community Education: " + e.getMessage());
        }
        return "redirect:/communityEducationList";
    }

    @GetMapping("/deleteCommunityEducation/{id}")
    public String deleteCommunityEducation(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            communityEducationService.deleteCommunityEducation(id);
            redirectAttributes.addFlashAttribute("message", "Community Education successfully deleted!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error deleting Community Education: " + e.getMessage());
        }
        return "redirect:/communityEducationList";
    }
}
