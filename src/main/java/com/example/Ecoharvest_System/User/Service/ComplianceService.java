package com.example.Ecoharvest_System.User.Service;

import com.example.Ecoharvest_System.User.Model.ComplianceModel;
import com.example.Ecoharvest_System.User.Repository.ComplianceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ComplianceService {

    @Autowired
    private ComplianceRepository complianceRepository;

    @Transactional
    public ComplianceModel createCompliance(ComplianceModel compliance) {
        return complianceRepository.save(compliance);
    }

    // Method to convert and save the document as a byte array
    public byte[] convertDocument(MultipartFile file) throws IOException {
        return file.getBytes();
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
