package com.example.Ecoharvest_System.User.Controllers;

import com.example.Ecoharvest_System.Admin.Model.BlogPostModel;
import com.example.Ecoharvest_System.Admin.Service.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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


    // User Dashboard
    @GetMapping("/userDashboard")
    public String userDashboard(Model model, HttpSession session) {
        UsersModel loggedInUser = (UsersModel) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != UsersModel.Role.USER) {
            return "redirect:/login";
        }

        model.addAttribute("userName", loggedInUser.getName());

        // Fetch all blog posts and format the createdAt dates
        List<BlogPostModel> blogPosts = blogPostService.findAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        List<Map<String, Object>> blogPostData = blogPosts.stream().map(post -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", post.getId());
            map.put("title", post.getTitle());
            map.put("featuredImage", post.getFeaturedImage());
            map.put("tags", post.getTags());
            map.put("createdAt", post.getCreatedAt().format(formatter));
            return map;
        }).collect(Collectors.toList());

        model.addAttribute("blogPosts", blogPostData);

        return "User/dashboard";
    }

}