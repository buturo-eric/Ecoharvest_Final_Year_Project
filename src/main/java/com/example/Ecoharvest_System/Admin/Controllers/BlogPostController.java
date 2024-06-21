package com.example.Ecoharvest_System.Admin.Controllers;

import com.example.Ecoharvest_System.Admin.Model.BlogPostModel;
import com.example.Ecoharvest_System.Admin.Service.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class BlogPostController {

    @Autowired
    private BlogPostService blogPostService;

    @GetMapping("/blogDashboard")
    public String blogDashboard(Model model) {
        List<BlogPostModel> posts = blogPostService.findAll(); // Assuming there's a method to find all posts
        model.addAttribute("posts", posts);
        return "Admin/blogDashboard"; // This should match the path and name of your Thymeleaf template
    }

    @GetMapping
    public String getAllBlogPosts(Model model) {
        model.addAttribute("blogPosts", blogPostService.findAll());
        return "Admin/blogDashboard";  // Path to the Thymeleaf template listing blog posts
    }

    @GetMapping("/createBlog")
    public String showCreateForm(Model model) {
        model.addAttribute("blogPost", new BlogPostModel());
        return "Admin/CreateBlogPost";  // Path to the Thymeleaf template for creating a blog post
    }

    @PostMapping("/createBlog")
    public String createBlogPost(BlogPostModel blogPost) {
        blogPostService.save(blogPost);
        return "redirect:/blogDashboard";  // Redirect to the listing page
    }

    @GetMapping("/editBlog/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<BlogPostModel> blogPost = blogPostService.findById(id);
        blogPost.ifPresent(b -> model.addAttribute("blogPost", b));
        return "Admin/EditBlogPost";  // Make sure this matches your Thymeleaf template's name and location
    }

    @PostMapping("/updateBlog")
    public String updateBlogPost(@ModelAttribute BlogPostModel blogPost) {
        blogPostService.save(blogPost);
        return "redirect:/blogDashboard";  // Redirect to the listing page
    }

    @GetMapping("/deleteBlog/{id}")
    public String deleteBlogPost(@PathVariable Long id) {
        blogPostService.deleteById(id);
        return "redirect:/blogDashboard";  // Redirect to the listing page after deletion
    }
}
