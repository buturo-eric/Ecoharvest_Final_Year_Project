package com.example.Ecoharvest_System.User.Service;

import com.example.Ecoharvest_System.User.Model.TaskModel;
import com.example.Ecoharvest_System.User.Repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Transactional
    public TaskModel saveTask(TaskModel task) {
        return taskRepository.save(task);
    }

    public TaskModel getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public List<TaskModel> getAllTasks() {
        return taskRepository.findAll();
    }

    @Transactional
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    // Method to convert and save the document as a byte array
    public byte[] convertDocument(MultipartFile file) throws IOException {
        return file.getBytes();
    }
    public Map<String, Long> getTaskStatusCounts() {
        Map<String, Long> statusCounts = new HashMap<>();
        statusCounts.put("Pending", taskRepository.countByStatus("Pending"));
        statusCounts.put("InProgress", taskRepository.countByStatus("InProgress"));
        statusCounts.put("Completed", taskRepository.countByStatus("Completed"));
        return statusCounts;
    }
    public Map<String, Long> getTaskOccurrenceCounts() {
        Map<String, Long> OccurrenceCounts = new HashMap<>();
        OccurrenceCounts.put("Daily", taskRepository.countByOccurrence("Daily"));
        OccurrenceCounts.put("Weekly", taskRepository.countByOccurrence("Weekly"));
        OccurrenceCounts.put("Monthly", taskRepository.countByOccurrence("Monthly"));
        OccurrenceCounts.put("Yearly", taskRepository.countByOccurrence("Yearly"));
        return OccurrenceCounts;
    }
    public Map<String, Long> getTaskCountsByCompliance() {
        List<Object[]> results = taskRepository.countTasksByCompliance();
        Map<String, Long> complianceTaskCounts = new HashMap<>();
        for (Object[] result : results) {
            complianceTaskCounts.put((String) result[0], (Long) result[1]);
        }
        return complianceTaskCounts;
    }
}
