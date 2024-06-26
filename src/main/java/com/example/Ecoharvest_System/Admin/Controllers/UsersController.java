package com.example.Ecoharvest_System.Admin.Controllers;

import com.example.Ecoharvest_System.Admin.Model.BlogPostModel;
import com.example.Ecoharvest_System.Admin.Model.UsersModel;
import com.example.Ecoharvest_System.Admin.Service.BlogPostService;
import com.example.Ecoharvest_System.Admin.Service.UsersService;
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

//    @GetMapping("/adminDashboard")
//    public String adminDashboard(Model model) {
//        // Get today's counts
//        long todayPostsCount = blogPostService.countTodayPosts();
//        long todayUsersCount = usersService.countTodayUsers();
//
//        // Get previous day's counts
//        long previousDayPostsCount = blogPostService.countPreviousDayPosts();
//        long previousDayUsersCount = usersService.countPreviousDayUsers();
//
//        // Calculate percentage changes
//        double postsPercentageChange = blogPostService.calculatePostPercentageChange(todayPostsCount, previousDayPostsCount);
//        double usersPercentageChange = usersService.calculateUserPercentageChange(todayUsersCount, previousDayUsersCount);
//
//        // Fetch all blog posts and format the createdAt dates
//        List<BlogPostModel> blogPosts = blogPostService.findAll();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//        List<Map<String, Object>> blogPostData = blogPosts.stream().map(post -> {
//            Map<String, Object> map = new HashMap<>();
//            map.put("id", post.getId());
//            map.put("title", post.getTitle());
//            map.put("featuredImage", post.getFeaturedImage());
//            map.put("tags", post.getTags());
//            map.put("createdAt", post.getCreatedAt().format(formatter));
//            return map;
//        }).collect(Collectors.toList());
//
//        // Get total counts
//        long totalPostsCount = blogPostService.countAllPosts();
//        long totalUsersCount = usersService.countAllUsers();
//
//        // Add counts, percentage changes, total counts, and blog posts to the model
//        model.addAttribute("todayPostsCount", todayPostsCount);
//        model.addAttribute("todayUsersCount", todayUsersCount);
//        model.addAttribute("postsPercentageChange", String.format("%.2f%%", postsPercentageChange));
//        model.addAttribute("usersPercentageChange", String.format("%.2f%%", usersPercentageChange));
//        model.addAttribute("blogPosts", blogPostData);
//        model.addAttribute("totalPostsCount", totalPostsCount);
//        model.addAttribute("totalUsersCount", totalUsersCount);
//
//        return "Admin/dashboard";
//    }

    @GetMapping("/adminDashboard")
    public String adminDashboard(Model model, HttpSession session) {
        UsersModel loggedInUser = (UsersModel) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != UsersModel.Role.ADMIN) {
            return "redirect:/login";
        }
        // Get today's counts
        long todayPostsCount = blogPostService.countTodayPosts();
        long todayUsersCount = usersService.countTodayUsers();

        // Get previous day's counts
        long previousDayPostsCount = blogPostService.countPreviousDayPosts();
        long previousDayUsersCount = usersService.countPreviousDayUsers();

        // Calculate percentage changes
        double postsPercentageChange = blogPostService.calculatePostPercentageChange(todayPostsCount, previousDayPostsCount);
        double usersPercentageChange = usersService.calculateUserPercentageChange(todayUsersCount, previousDayUsersCount);

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

        // Get total counts
        long totalPostsCount = blogPostService.countAllPosts();
        long totalUsersCount = usersService.countAllUsers();

        // Add counts, percentage changes, total counts, and blog posts to the model
        model.addAttribute("todayPostsCount", todayPostsCount);
        model.addAttribute("todayUsersCount", todayUsersCount);
        model.addAttribute("postsPercentageChange", String.format("%.2f%%", postsPercentageChange));
        model.addAttribute("usersPercentageChange", String.format("%.2f%%", usersPercentageChange));
        model.addAttribute("blogPosts", blogPostData);
        model.addAttribute("totalPostsCount", totalPostsCount);
        model.addAttribute("totalUsersCount", totalUsersCount);

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
    public String loginUser(@ModelAttribute("userModel") UsersModel usersModel, Model model, HttpSession session){
        UsersModel loginUser = usersService.userLogin(usersModel);

        if (loginUser != null) {
            switch (loginUser.getRole()) {
                case ADMIN:
                    model.addAttribute("success", "Admin Logged in");
                    session.setAttribute("loggedInUser", loginUser); // Corrected attribute name
                    System.out.println("Admin logged in");
                    return "redirect:/adminDashboard";
                case USER:
                    model.addAttribute("success", "User Logged in");
                    session.setAttribute("loggedInUser", loginUser); // Corrected attribute name
                    System.out.println("User logged in");
                    return "redirect:/userDashboard";
                case Compliance:
                    model.addAttribute("success", "Compliance Logged in");
                    session.setAttribute("loggedInUser", loginUser); // Corrected attribute name
                    System.out.println("Compliance logged in");
                    return "redirect:/ComplianceDashboard";
                default:
                    model.addAttribute("error", "Role doesn't Exist");
                    System.out.println("Role doesn't exist");
                    return "redirect:/login";
            }
        } else {
            model.addAttribute("error", "Invalid email or password");
            System.out.println("Invalid email or password");
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
