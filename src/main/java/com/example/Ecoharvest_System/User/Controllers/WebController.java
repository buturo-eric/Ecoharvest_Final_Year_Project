package com.example.Ecoharvest_System.User.Controllers;

import com.example.Ecoharvest_System.Admin.Model.BlogPostModel;
import com.example.Ecoharvest_System.Admin.Model.CommunityEducationModel;
import com.example.Ecoharvest_System.Admin.Model.UsersModel;
import com.example.Ecoharvest_System.Admin.Service.BlogPostService;
import com.example.Ecoharvest_System.Admin.Service.CommunityEducationService;
import com.example.Ecoharvest_System.Admin.Service.UsersService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class WebController {
    @Autowired
    private UsersService usersService;

    @Autowired
    private BlogPostService blogPostService;

    @Autowired
    private CommunityEducationService communityEducationService;

    @GetMapping("/")
    public String home() {
        return "User/index";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("UsersModel", new UsersModel());
        return "User/login";
    }

    @GetMapping("/signup")
    public String signup(Model model){
        model.addAttribute("UsersModel", new UsersModel());
        return "User/signup";
    }

    @GetMapping("/aiAssistance")
    public String aiAssistance() {
        return "User/aiAssistance";
    }

    @PostMapping("/createUser")
    public String createUser(UsersModel usersModel) {
        usersService.addUsersModel(usersModel);
        return "redirect:/login"; // Redirect to the home page or any other page after submission
    }

    @GetMapping("/calendar")
    public String Calendar(Model model) throws JsonProcessingException {
        List<CommunityEducationModel> allEntries = communityEducationService.getAllCommunityEducations();

        // Convert entries to JSON for FullCalendar
        List<Map<String, Object>> events = new ArrayList<>();
        for (CommunityEducationModel entry : allEntries) {
            Map<String, Object> event = new HashMap<>();
            event.put("title", entry.getName());
            event.put("start", entry.getDate().toString() + "T" + entry.getTime().toString());
            event.put("description", entry.getDescription());
            event.put("url", entry.getLink()); // Assuming you have a link attribute
            events.add(event);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Register the module
        String eventsJson = objectMapper.writeValueAsString(events);

        model.addAttribute("eventsJson", eventsJson);

        return "User/UserCalendar";
    }


}