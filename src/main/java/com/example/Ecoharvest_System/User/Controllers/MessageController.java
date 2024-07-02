package com.example.Ecoharvest_System.User.Controllers;

import com.example.Ecoharvest_System.Admin.Model.UsersModel;
import com.example.Ecoharvest_System.Admin.Service.UsersService;
import com.example.Ecoharvest_System.User.Model.MessageModel;
import com.example.Ecoharvest_System.User.Service.MessageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UsersService usersService;

    @GetMapping
    public String getMessages(Model model, HttpSession session) {
        UsersModel loggedInUser = (UsersModel) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            model.addAttribute("userId", loggedInUser.getId());
            model.addAttribute("role", loggedInUser.getRole());
        } else {
            return "redirect:/login";
        }

        List<MessageModel> messages = messageService.findAllMessages();
        model.addAttribute("messages", messages);
        return "User/messages";
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
        return "redirect:/messages";
    }

    @GetMapping("/replies/{id}")
    public String getReplies(@PathVariable Long id, Model model) {
        MessageModel message = messageService.findById(id);
        if (message == null) {
            return "redirect:/messages?error=message_not_found";
        }

        List<MessageModel> replies = messageService.findReplies(message);
        model.addAttribute("message", message);
        model.addAttribute("replies", replies);
        return "replies";
    }
}
