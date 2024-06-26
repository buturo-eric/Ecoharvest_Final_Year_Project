package com.example.Ecoharvest_System.User.Model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "compliance")
public class ComplianceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String complianceName;

    @Column(name = "featured_image", length = 255)
    private String featuredImage;

    @Lob  // Large Object for storing large data
    @Column(nullable = false)
    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    @Lob  // Large Object for storing large data
    @Column(columnDefinition = "LONGBLOB")
    private byte[] complianceDocument;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComplianceName() {
        return complianceName;
    }

    public void setComplianceName(String complianceName) {
        this.complianceName = complianceName;
    }

    public String getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public byte[] getComplianceDocument() {
        return complianceDocument;
    }

    public void setComplianceDocument(byte[] complianceDocument) {
        this.complianceDocument = complianceDocument;
    }
}
