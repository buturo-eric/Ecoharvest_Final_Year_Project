package com.example.Ecoharvest_System.User.Model;

import com.example.Ecoharvest_System.Admin.Model.UsersModel;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "messages")
public class MessageModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UsersModel user;

    @Column(nullable = false)
    private String message;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "reply_id")
    private MessageModel reply;

    @OneToMany(mappedBy = "reply", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<MessageModel> replies = new HashSet<>();

    @Column(name = "is_visible", nullable = false)
    private boolean isVisible;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsersModel getUser() {
        return user;
    }

    public void setUser(UsersModel user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageModel getReply() {
        return reply;
    }

    public void setReply(MessageModel reply) {
        this.reply = reply;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Set<MessageModel> getReplies() {
        return replies;
    }

    public void setReplies(Set<MessageModel> replies) {
        this.replies = replies;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
