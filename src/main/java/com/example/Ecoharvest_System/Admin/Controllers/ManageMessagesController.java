package com.example.Ecoharvest_System.Admin.Controllers;

import com.example.Ecoharvest_System.Admin.Model.UsersModel;
import com.example.Ecoharvest_System.Admin.Service.UsersService;
import com.example.Ecoharvest_System.User.Model.MessageModel;
import com.example.Ecoharvest_System.User.Service.MessageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/manageMessages")
public class ManageMessagesController {
    @Autowired
    private MessageService messageService;

    @Autowired
    private UsersService usersService;

    @GetMapping
    public String getMessages(Model model, HttpSession session) {
        UsersModel loggedInUser = (UsersModel) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            model.addAttribute("userId", loggedInUser.getId());
        } else {
            return "redirect:/login";
        }

        List<MessageModel> messages;
        messages = messageService.findAllMessagesForAdmin(); // Fetch all messages for admin

        model.addAttribute("messages", messages);
        return "Admin/ManageMessages";
    }
    @PostMapping("/post")
    public String postMessage(@RequestParam int userId, @RequestParam String message, @RequestParam(required = false) Long replyId) {
        MessageModel newMessage = new MessageModel();
        UsersModel user = usersService.findById(userId);
        newMessage.setUser(user);
        newMessage.setMessage(message);
        newMessage.setDateTime(LocalDateTime.now());
        if (replyId != null) {
            MessageModel reply = messageService.findById(replyId);
            newMessage.setReply(reply);
        }
        messageService.saveMessage(newMessage);
        return "redirect:/manageMessages";
    }
    @PostMapping("/updateVisibility")
    public String updateMessageVisibility(@RequestParam("id") Long id, @RequestParam("isVisible") boolean isVisible) {
        messageService.updateMessageVisibility(id, isVisible);
        return "redirect:/manageMessages";
    }
}
