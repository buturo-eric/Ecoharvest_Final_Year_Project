package com.example.Ecoharvest_System.User.Service;

import com.example.Ecoharvest_System.User.Model.ComplianceModel;
import com.example.Ecoharvest_System.User.Repository.ComplianceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    public ComplianceModel getComplianceById(Long id) {
        return complianceRepository.findById(id).orElseThrow(() -> new RuntimeException("Compliance not found"));
    }
    @Transactional
    public ComplianceModel updateCompliance(ComplianceModel compliance) {
        Optional<ComplianceModel> existingComplianceOpt = complianceRepository.findById(compliance.getId());
        if (existingComplianceOpt.isPresent()) {
            ComplianceModel existingCompliance = existingComplianceOpt.get();
            existingCompliance.setComplianceName(compliance.getComplianceName());
            existingCompliance.setDescription(compliance.getDescription());
            existingCompliance.setStartDate(compliance.getStartDate());
            existingCompliance.setEndDate(compliance.getEndDate());
            existingCompliance.setFeaturedImage(compliance.getFeaturedImage());
            if (compliance.getComplianceDocument() != null) {
                existingCompliance.setComplianceDocument(compliance.getComplianceDocument());
            }
            return complianceRepository.save(existingCompliance);
        } else {
            throw new RuntimeException("Compliance not found with id: " + compliance.getId());
        }
    }
    @Transactional(readOnly = true)
    public List<ComplianceModel> findCompliancesStartingToday() {
        LocalDate today = LocalDate.now();
        return complianceRepository.findByStartDate(today);
    }
    @Transactional(readOnly = true)
    public List<ComplianceModel> findCompliancesEndingToday() {
        LocalDate today = LocalDate.now();
        return complianceRepository.findByEndDate(today);
    }

}
