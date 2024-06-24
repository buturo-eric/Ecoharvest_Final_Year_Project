package com.example.Ecoharvest_System.Admin.Model;

import jakarta.persistence.*;

@Entity
public class ChecklistItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String description;
    private boolean isCompleted;

    @ManyToOne
    @JoinColumn(name = "checklist_id")
    private ComplianceChecklistModel complianceChecklist;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public ComplianceChecklistModel getComplianceChecklist() {
        return complianceChecklist;
    }

    public void setComplianceChecklist(ComplianceChecklistModel complianceChecklist) {
        this.complianceChecklist = complianceChecklist;
    }
}