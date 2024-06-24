package com.example.Ecoharvest_System.Admin.Model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class ComplianceStatusModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String farmId;
    private boolean isCompliant;
    private Date nextDeadline;
    private String notes;

    // Standard getters and setters

    public ComplianceStatusModel() {
    }

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

    public boolean isCompliant() {
        return isCompliant;
    }

    public void setCompliant(boolean compliant) {
        isCompliant = compliant;
    }

    public Date getNextDeadline() {
        return nextDeadline;
    }

    public void setNextDeadline(Date nextDeadline) {
        this.nextDeadline = nextDeadline;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
