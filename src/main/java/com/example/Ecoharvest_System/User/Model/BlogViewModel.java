package com.example.Ecoharvest_System.User.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "blog_views")
public class BlogViewModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "blog_id", nullable = false)
    private Long blogId;

    @Column(name = "viewed_at", nullable = false)
    private LocalDateTime viewedAt;

    // Constructors
    public BlogViewModel() {
        this.viewedAt = LocalDateTime.now(); // Set the current time when a new view is created
    }

    public BlogViewModel(int userId, Long blogId) {
        this.userId = userId;
        this.blogId = blogId;
        this.viewedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public LocalDateTime getViewedAt() {
        return viewedAt;
    }

    public void setViewedAt(LocalDateTime viewedAt) {
        this.viewedAt = viewedAt;
    }
}
