package com.example.Ecoharvest_System.User.Service;

import com.example.Ecoharvest_System.User.Model.TaskModel;
import com.example.Ecoharvest_System.User.Repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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
}
