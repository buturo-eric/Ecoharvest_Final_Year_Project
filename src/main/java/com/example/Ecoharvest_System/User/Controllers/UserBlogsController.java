package com.example.Ecoharvest_System.User.Controllers;

import com.example.Ecoharvest_System.Admin.Model.BlogPostModel;
import com.example.Ecoharvest_System.Admin.Service.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserBlogsController {

    @Autowired
    private BlogPostService blogPostService;

    @GetMapping("/blogs")
    public String getBlogPosts(Model model) {
        List<BlogPostModel> posts = blogPostService.findAll();
        model.addAttribute("posts", posts);
        return "User/UserBlogs";  // Name of the Thymeleaf template
    }
}