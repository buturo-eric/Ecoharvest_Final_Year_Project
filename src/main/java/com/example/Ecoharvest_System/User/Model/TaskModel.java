package com.example.Ecoharvest_System.User.Model;

import jakarta.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "task")
public class TaskModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String taskName;

    @Temporal(TemporalType.DATE)
    @Column(name = "task_date") // Changed from 'date' to 'task_date'
    private LocalDate task_date;

    @Column
    private String status;

    @Column(name = "task_by") // Changed from 'by' to 'task_by'
    private String task_by; // This could be the ID or name of the person responsible for the task

    @Column
    private String taskDocument; // URL or path to the document

    @Column
    private String occurrence;

    @ManyToOne
    @JoinColumn(name = "compliance_id", nullable = false)
    private ComplianceModel compliance;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public LocalDate getTask_date() {
        return task_date;
    }

    public void setTask_date(LocalDate task_date) {
        this.task_date = task_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTask_by() {
        return task_by;
    }

    public void setTask_by(String task_by) {
        this.task_by = task_by;
    }

    public String getTaskDocument() {
        return taskDocument;
    }

    public void setTaskDocument(String taskDocument) {
        this.taskDocument = taskDocument;
    }

    public String getOccurrence() {
        return occurrence;
    }

    public void setOccurrence(String occurrence) {
        this.occurrence = occurrence;
    }

    public ComplianceModel getCompliance() {
        return compliance;
    }

    public void setCompliance(ComplianceModel compliance) {
        this.compliance = compliance;
    }
}
