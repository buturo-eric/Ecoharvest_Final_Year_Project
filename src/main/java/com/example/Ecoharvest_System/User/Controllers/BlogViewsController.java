package com.example.Ecoharvest_System.User.Controllers;

import com.example.Ecoharvest_System.Admin.Model.BlogPostModel;
import com.example.Ecoharvest_System.Admin.Model.UsersModel;
import com.example.Ecoharvest_System.User.Model.BlogViewModel;
import com.example.Ecoharvest_System.Admin.Service.BlogPostService;
import com.example.Ecoharvest_System.User.Service.BlogViewService;
import jakarta.servlet.http.HttpSession;
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

@Controller
public class BlogViewsController {

    @Autowired
    private BlogPostService blogPostService;

    @Autowired
    private BlogViewService blogViewService;

    @GetMapping("/blogs")
    public String userblogs(Model model, HttpSession session) {
        UsersModel loggedInUser = (UsersModel) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != UsersModel.Role.USER) {
            return "redirect:/login";
        }

        model.addAttribute("userName", loggedInUser.getName());

        List<BlogPostModel> blogPosts = blogPostService.findAll();

        List<BlogPostModel> readBlogs = blogPosts.stream()
                .filter(post -> blogViewService.isBlogViewedByUser(loggedInUser.getId(), post.getId()))
                .collect(Collectors.toList());

        List<BlogPostModel> unreadBlogs = blogPosts.stream()
                .filter(post -> !blogViewService.isBlogViewedByUser(loggedInUser.getId(), post.getId()))
                .collect(Collectors.toList());

        model.addAttribute("readBlogs", readBlogs);
        model.addAttribute("unreadBlogs", unreadBlogs);

        return "User/BlogPage";
    }

    @GetMapping("/blogPost/{id}")
    public String getBlogPost(@PathVariable Long id, Model model, @SessionAttribute("loggedInUser") UsersModel loggedInUser) {
        Optional<BlogPostModel> blogPost = blogPostService.findById(id);
        if (blogPost.isPresent()) {
            // Save the view
            BlogViewModel blogView = new BlogViewModel(loggedInUser.getId(), id);
            blogViewService.saveBlogView(blogView);

            BlogPostModel post = blogPost.get();
            String[] paragraphs = post.getContent().split("\n");
            model.addAttribute("blogPost", post);
            model.addAttribute("paragraphs", paragraphs);
            return "User/Blog"; // Path to the Thymeleaf template for viewing a blog post
        } else {
            // Handle the case where the blog post is not found
            model.addAttribute("error", "Blog post not found");
            return "User/error";
        }
    }


}
