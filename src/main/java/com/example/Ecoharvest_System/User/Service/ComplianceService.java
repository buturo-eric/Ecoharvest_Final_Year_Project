package com.example.Ecoharvest_System.User.Service;

import com.example.Ecoharvest_System.User.Model.ComplianceModel;
import com.example.Ecoharvest_System.User.Repository.ComplianceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ComplianceService {

    @Autowired
    private ComplianceRepository complianceRepository;

    // Path where the files will be stored
    private static final String DOCUMENTS_FOLDER = "path/to/your/documents/folder/";

    @Transactional
    public ComplianceModel createCompliance(ComplianceModel compliance) {
        return complianceRepository.save(compliance);
    }

    // Method to save the document and return the path or URL
    public String saveDocument(MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            // Ensure the directory exists
            File directory = new File(DOCUMENTS_FOLDER);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Construct the file path
            String originalFilename = file.getOriginalFilename();
            Path filePath = Paths.get(DOCUMENTS_FOLDER, originalFilename);
            // Save the file
            Files.copy(file.getInputStream(), filePath);

            // Return the path or URL to the file
            return filePath.toString();
        }
        return null; // Return null or throw an exception if no file was provided
    }

    @Transactional(readOnly = true)
    public List<ComplianceModel> findAllCompliances() {
        return complianceRepository.findAll();
    }

    @Transactional
    public void deleteCompliance(Long id) {
        complianceRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public ComplianceModel findById(Long id) {
        return complianceRepository.findById(id).orElse(null);  // Handling optional
    }
}
