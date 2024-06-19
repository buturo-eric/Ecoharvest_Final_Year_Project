package com.example.Ecoharvest_System.Admin.Controllers;

import com.example.Ecoharvest_System.Admin.Model.UsersModel;
import com.example.Ecoharvest_System.Admin.Service.UsersService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
public class UsersController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/adminDashboard")
    public String adminDashboard() {
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
        return "redirect:/"; // Redirect to the home page or any other page after submission
    }

    @PostMapping("/loginUser")
    public String loginUser(@ModelAttribute("userModel") UsersModel usersModel, Model model, HttpSession session){
        UsersModel loginUser = usersService.userLogin(usersModel);

        if (loginUser != null) {
            switch (loginUser.getRole()) {
                case ADMIN:
                    model.addAttribute("success", "Admin Logged in");
                    session.setAttribute("userModel", loginUser); // storing the whole user object might be more useful
                    return "redirect:/adminDashboard";
                case USER:
                    model.addAttribute("success", "User Logged in");
                    session.setAttribute("userModel", loginUser); // storing the whole user object
                    return "redirect:/userDashboard";
                default:
                    model.addAttribute("error", "Role doesn't Exist");
                    return "redirect:/login";
            }
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "redirect:/login";
        }
    }


    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes){
        session.invalidate();
        redirectAttributes.addFlashAttribute("logged_out", "Logged out successfully");
        return "redirect:/home";
    }

    @GetMapping("/allUsers")
    public String allUsers(Model model) {
        List<UsersModel> users = usersService.getAll(); // Assuming you have a method to get all users
        model.addAttribute("users", users);
        return "Admin/AllUsers"; // The name of the Thymeleaf template
    }


}
