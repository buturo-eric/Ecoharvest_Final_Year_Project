package com.example.Ecoharvest_System.User.Controllers;

import com.example.Ecoharvest_System.Admin.Model.BlogPostModel;
import com.example.Ecoharvest_System.Admin.Model.CommunityEducationModel;
import com.example.Ecoharvest_System.Admin.Service.BlogPostService;
import com.example.Ecoharvest_System.Admin.Service.CommunityEducationService;
import com.example.Ecoharvest_System.User.Service.BlogViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import com.example.Ecoharvest_System.Admin.Model.UsersModel;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserBlogsController {

    @Autowired
    private BlogPostService blogPostService;

    @Autowired
    private CommunityEducationService communityEducationService;

    @Autowired
    private BlogViewService blogViewService;


    // User Dashboard
    @GetMapping("/userDashboard")
    public String userDashboard(Model model, HttpSession session) {
        UsersModel loggedInUser = (UsersModel) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != UsersModel.Role.USER) {
            return "redirect:/login";
        }

        model.addAttribute("userName", loggedInUser.getName());

        LocalDate today = LocalDate.now();
        List<CommunityEducationModel> allEntries = communityEducationService.getAllCommunityEducations();

        long pastEventsCount = allEntries.stream()
                .filter(entry -> entry.getDate().isBefore(today))
                .count();

        long todayEventsCount = allEntries.stream()
                .filter(entry -> entry.getDate().isEqual(today))
                .count();

        long upcomingEventsCount = allEntries.stream()
                .filter(entry -> entry.getDate().isAfter(today))
                .count();

        model.addAttribute("pastEventsCount", pastEventsCount);
        model.addAttribute("todayEventsCount", todayEventsCount);
        model.addAttribute("upcomingEventsCount", upcomingEventsCount);

        // Fetch blog view data
        List<BlogPostModel> allBlogs = blogPostService.findAll();
        int userId = loggedInUser.getId();

        long readBlogsCount = allBlogs.stream()
                .filter(blog -> blogViewService.isBlogViewedByUser(userId, blog.getId()))
                .count();

        long unreadBlogsCount = allBlogs.size() - readBlogsCount;

        model.addAttribute("readBlogsCount", readBlogsCount);
        model.addAttribute("unreadBlogsCount", unreadBlogsCount);

        return "User/dashboard";
    }

}