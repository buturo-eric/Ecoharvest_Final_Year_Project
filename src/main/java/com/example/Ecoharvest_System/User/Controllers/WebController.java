package com.example.Ecoharvest_System.User.Controllers;

import com.example.Ecoharvest_System.Admin.Model.BlogPostModel;
import com.example.Ecoharvest_System.Admin.Model.UsersModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class WebController {
    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/userDashboard")
    public String userDashboard() {
        return "User/dashboard";
    }

    @GetMapping("/userBlogs")
    public String userBlogs() {
        return "User/UserBlogs"; // This should match the path and name of your Thymeleaf template
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("UsersModel", new UsersModel());
        return "User/login";
    }

    //Signup page
    @GetMapping("/signup")
    public String signup(Model model){
        model.addAttribute("UsersModel", new UsersModel());
        return "User/signup";
    }

    @GetMapping("/communityEducation")
    public String communityEducation() {
        return "User/CommunityEducation";
    }

}