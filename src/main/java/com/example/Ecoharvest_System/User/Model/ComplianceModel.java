package com.example.Ecoharvest_System.User.Model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

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

    @OneToMany(mappedBy = "compliance", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TaskModel> tasks;

    public List<TaskModel> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskModel> tasks) {
        this.tasks = tasks;
    }

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
