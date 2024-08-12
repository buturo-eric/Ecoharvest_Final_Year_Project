package com.example.Ecoharvest_System.Admin.Controllers;

import com.example.Ecoharvest_System.Admin.Model.BlogPostModel;
import com.example.Ecoharvest_System.Admin.Model.UsersModel;
import com.example.Ecoharvest_System.Admin.Service.BlogPostService;
import com.example.Ecoharvest_System.Admin.Service.UsersService;
import com.example.Ecoharvest_System.User.Service.BlogViewService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class UsersController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private BlogPostService blogPostService;

    @Autowired
    private BlogViewService blogViewService;

    @GetMapping("/adminDashboard")
    public String adminDashboard(Model model, HttpSession session) {
        UsersModel loggedInUser = (UsersModel) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != UsersModel.Role.ADMIN) {
            return "redirect:/login";
        }

        model.addAttribute("userId", loggedInUser.getId());
        model.addAttribute("userName", loggedInUser.getName());

        // Get today's counts
        long todayPostsCount = blogPostService.countTodayPosts();
        long todayUsersCount = usersService.countTodayUsers();

        // Get previous day's counts
        long previousDayPostsCount = blogPostService.countPreviousDayPosts();
        long previousDayUsersCount = usersService.countPreviousDayUsers();

        // Calculate percentage changes
        double postsPercentageChange = blogPostService.calculatePostPercentageChange(todayPostsCount, previousDayPostsCount);
        double usersPercentageChange = usersService.calculateUserPercentageChange(todayUsersCount, previousDayUsersCount);

        // Get total counts
        long totalPostsCount = blogPostService.countAllPosts();
        long totalUsersCount = usersService.countAllUsers();

        // Add counts, percentage changes, total counts, and blog posts to the model
        model.addAttribute("todayPostsCount", todayPostsCount);
        model.addAttribute("todayUsersCount", todayUsersCount);
        model.addAttribute("postsPercentageChange", String.format("%.2f%%", postsPercentageChange));
        model.addAttribute("usersPercentageChange", String.format("%.2f%%", usersPercentageChange));
        model.addAttribute("totalPostsCount", totalPostsCount);
        model.addAttribute("totalUsersCount", totalUsersCount);

        // Get blog views data
        List<BlogPostModel> allBlogs = blogPostService.findAll();
        List<Map<String, Object>> blogViewsData = allBlogs.stream().map(blog -> {
            Map<String, Object> data = new HashMap<>();
            data.put("title", blog.getTitle());
            data.put("views", blogViewService.countViewsByBlogId(blog.getId()));
            return data;
        }).collect(Collectors.toList());

        // Serialize blogViewsData to JSON string
        ObjectMapper mapper = new ObjectMapper();
        try {
            String blogViewsJson = mapper.writeValueAsString(blogViewsData);
            model.addAttribute("blogViewsData", blogViewsJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            // Handle error
        }

        // Get tag view statistics
        Map<String, Long> tagViewCounts = new HashMap<>();
        for (BlogPostModel blog : allBlogs) {
            if (blog.getTags() != null) {
                String[] tags = blog.getTags().split(",");
                for (String tag : tags) {
                    tag = tag.trim();
                    long views = blogViewService.countViewsByBlogId(blog.getId());
                    tagViewCounts.put(tag, tagViewCounts.getOrDefault(tag, 0L) + views);
                }
            }
        }

        // Serialize tagViewCounts to JSON string
        try {
            String tagViewCountsJson = mapper.writeValueAsString(tagViewCounts);
            model.addAttribute("tagViewCounts", tagViewCountsJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            // Handle error
        }

        return "Admin/dashboard";
    }

    @GetMapping("/addUser")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new UsersModel());
        return "Admin/CreateUser"; // Make sure this matches the HTML file name (without .html extension)
    }

    @PostMapping("/addUser")
    public String addUser(UsersModel usersModel) {
        usersService.addUsersModel(usersModel);
        return "redirect:/allUsers"; // Redirect to the home page or any other page after submission
    }

    @PostMapping("/loginUser")
    public String loginUser(@ModelAttribute("userModel") UsersModel usersModel, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        UsersModel loginUser = usersService.userLogin(usersModel);

        if (loginUser != null) {
            switch (loginUser.getRole()) {
                case ADMIN:
                    session.setAttribute("loggedInUser", loginUser);
                    return "redirect:/adminDashboard";
                case USER:
                    session.setAttribute("loggedInUser", loginUser);
                    return "redirect:/userDashboard";
                case Compliance:
                    session.setAttribute("loggedInUser", loginUser);
                    return "redirect:/ComplianceDashboard";
                default:
                    redirectAttributes.addFlashAttribute("error", "Role doesn't Exist");
                    return "redirect:/login";
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid email or password");
            return "redirect:/login";
        }
    }


    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes){
        session.invalidate();
        redirectAttributes.addFlashAttribute("logged_out", "Logged out successfully");
        return "redirect:/login";
    }

    @GetMapping("/allUsers")
    public String allUsers(@RequestParam(value = "time", required = false) String time, Model model) {
        List<UsersModel> users;
        if (time == null) {
            time = "today";  // Default to today if no input is provided
        }

        switch (time) {
            case "3days":
                users = usersService.getUsersFromLastDays(3);
                break;
            case "1week":
                users = usersService.getUsersFromLastDays(7);
                break;
            case "1month":
                users = usersService.getUsersFromLastDays(28);
                break;
            case "3month":
                users = usersService.getUsersFromLastDays(90);
                break;
            case "6month":
                users = usersService.getUsersFromLastDays(183);
                break;
            case "today":
            default:
                users = usersService.getUsersToday();
                break;
        }
        model.addAttribute("users", users);
        return "Admin/AllUsers"; // The name of the Thymeleaf template
    }

    // Delete User
    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        usersService.deleteUsersModel(id);
        return "redirect:/allUsers"; // Assuming you have a mapping to show all users
    }

    // Redirect to Edit Form
    @GetMapping("/editUser/{id}")
    public String editUserForm(@PathVariable("id") Integer id, Model model) {
        UsersModel user = usersService.listById(id);
        model.addAttribute("user", user);
        return "Admin/editUser"; // Name of the HTML file for editing users
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("user") UsersModel user) {
        usersService.updateUsersModel(user, user.getId());
        return "redirect:/allUsers"; // Redirect to the listing page
    }
}
