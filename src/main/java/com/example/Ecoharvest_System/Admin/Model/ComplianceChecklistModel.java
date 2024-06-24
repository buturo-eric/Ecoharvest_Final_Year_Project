package com.example.Ecoharvest_System.Admin.Model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class ComplianceChecklistModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String farmId;
    private Date creationDate;
    private boolean isComplete;
//    private String checklistItems; // JSON format or could be a @OneToMany relation to another table
    @OneToMany(mappedBy = "complianceChecklist", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ChecklistItem> checklistItems;

    // Standard getters and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFarmId() {
        return farmId;
    }

    public void setFarmId(String farmId) {
        this.farmId = farmId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public List<ChecklistItem> getChecklistItems() {
        return checklistItems;
    }

    public void setChecklistItems(List<ChecklistItem> checklistItems) {
        this.checklistItems = checklistItems;
    }
}
