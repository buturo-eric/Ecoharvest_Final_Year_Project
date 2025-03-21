package com.example.Ecoharvest_System.User.Controllers;

import com.example.Ecoharvest_System.Admin.Model.UsersModel;
import com.example.Ecoharvest_System.User.Model.ComplianceModel;
import com.example.Ecoharvest_System.User.Model.TaskModel;
import com.example.Ecoharvest_System.User.Service.ComplianceService;
import com.example.Ecoharvest_System.User.Service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private ComplianceService complianceService;

    @GetMapping("/tasksList")
    public String listTasks(Model model) {
        model.addAttribute("tasks", taskService.getAllTasks());
        return "User/tasksList";  // path to your Thymeleaf template
    }

    @GetMapping("/createTask/{id}")
    public String createTask(@PathVariable("id") Long complianceId, Model model, HttpSession session) {
        ComplianceModel compliance = complianceService.getComplianceById(complianceId); // Fetch the full compliance
        TaskModel task = new TaskModel();
        task.setCompliance(compliance);
        UsersModel loggedInUser = (UsersModel) session.getAttribute("loggedInUser");
        task.setTask_by(loggedInUser.getName()); // Assuming there is a setTaskBy method
        model.addAttribute("task", task);
        model.addAttribute("userName", loggedInUser.getName());
        return "User/createTask";
    }


    @PostMapping("/task/save")
    public String saveTask(
            @ModelAttribute("task") TaskModel task,
            @RequestParam("taskDocumentFile") MultipartFile file, // This should match the file input name in the form
            RedirectAttributes redirectAttributes) {

        if (!file.isEmpty()) {
            try {
                // Convert the file to byte array and set it to the model
                byte[] docBytes = taskService.convertDocument(file);
                task.setTaskDocument(docBytes); // Assuming taskDocument is a byte[] field
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("message", "File upload failed: " + e.getMessage());
                return "redirect:/createTask/" + task.getCompliance().getId();
            }
        }

        try {
            taskService.saveTask(task);
            redirectAttributes.addFlashAttribute("message", "Task successfully created!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error saving task: " + e.getMessage());
        }

        return "redirect:/tasksList";  // Redirect to prevent duplicate submissions
    }

    @GetMapping("/editTask/{id}")
    public String editTask(@PathVariable Long id, Model model) {
        model.addAttribute("task", taskService.getTaskById(id));
        return "User/editTask";
    }

    @PostMapping("/updateTask")
    public String updateTask(
            @ModelAttribute("task") TaskModel task,
            @RequestParam("taskDocumentFile") MultipartFile file,
            RedirectAttributes redirectAttributes) {

        if (!file.isEmpty()) {
            try {
                byte[] docBytes = taskService.convertDocument(file);
                task.setTaskDocument(docBytes);
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("message", "File upload failed: " + e.getMessage());
                return "redirect:/editTask/" + task.getId();
            }
        } else {
            TaskModel existingTask = taskService.getTaskById(task.getId());
            if (existingTask != null) {
                task.setTaskDocument(existingTask.getTaskDocument());
            }
        }

        try {
            taskService.saveTask(task);
            redirectAttributes.addFlashAttribute("message", "Task successfully updated!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error updating task: " + e.getMessage());
        }

        return "redirect:/tasksList";
    }

    @GetMapping("/deleteTask/{id}")
    public String deleteTask(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            taskService.deleteTask(id);
            redirectAttributes.addFlashAttribute("message", "Task successfully deleted!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error deleting task: " + e.getMessage());
        }
        return "redirect:/tasksList";
    }
    @GetMapping("/downloadTask/{id}")
    public ResponseEntity<byte[]> downloadTaskDocument(@PathVariable Long id) {
        TaskModel task = taskService.findById(id);
        if (task == null || task.getTaskDocument() == null) {
            // Handle the case where the task or document is not found
            return ResponseEntity.notFound().build();
        }

        byte[] document = task.getTaskDocument();
        String fileName = task.getTaskName() + ".pdf"; // Assuming the document is a PDF

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(document);
    }

}
