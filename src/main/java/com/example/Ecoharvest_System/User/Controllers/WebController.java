package com.example.Ecoharvest_System.User.Controllers;

import com.example.Ecoharvest_System.Admin.Model.UsersModel;
import com.example.Ecoharvest_System.Admin.Service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WebController {
    @Autowired
    private UsersService usersService;
    @GetMapping("/")
    public String home() {
        return "User/index";
    }

    @GetMapping("/userDashboard")
    public String userDashboard() {
        return "User/dashboard";
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

    @GetMapping("/communityEducation")
    public String communityEducation() {
        return "User/CommunityEducation";
    }

    @PostMapping("/createUser")
    public String createUser(UsersModel usersModel) {
        usersService.addUsersModel(usersModel);
        return "redirect:/login"; // Redirect to the home page or any other page after submission
    }

}