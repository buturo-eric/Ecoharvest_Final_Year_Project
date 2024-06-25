package com.example.Ecoharvest_System.User.Model;

import jakarta.persistence.*;
import java.util.Date;

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

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column
    private String complianceDocument; // URL or path to the document

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getComplianceDocument() {
        return complianceDocument;
    }

    public void setComplianceDocument(String complianceDocument) {
        this.complianceDocument = complianceDocument;
    }
}
