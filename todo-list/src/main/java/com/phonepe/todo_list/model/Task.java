package com.phonepe.todo_list.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class Task {
    private final String id;
    private String title;
    private String description;
    private LocalDate deadline;
    private Set<String> tags;
    private boolean completed;
    private LocalDate scheduledDate;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private final String userId;

    public Task(String id, String title, String description, LocalDate deadline,
                Set<String> tags, LocalDate scheduledDate, String userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.tags = tags != null ? new HashSet<>(tags) : new HashSet<>();
        this.completed = false;
        this.scheduledDate = scheduledDate;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
        this.userId = userId;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.updatedAt = LocalDateTime.now();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
        this.updatedAt = LocalDateTime.now();
    }

    public Set<String> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public void setTags(Set<String> tags) {
        this.tags = tags != null ? new HashSet<>(tags) : new HashSet<>();
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDate getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDate scheduledDate) {
        this.scheduledDate = scheduledDate;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id.equals(task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", deadline=" + deadline +
                ", tags=" + tags +
                ", completed=" + completed +
                ", scheduledDate=" + scheduledDate +
                ", userId='" + userId + '\'' +
                '}';
    }
}
