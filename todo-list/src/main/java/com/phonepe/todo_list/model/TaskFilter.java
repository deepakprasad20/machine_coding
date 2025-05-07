package com.phonepe.todo_list.model;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class TaskFilter {
    private final String userId;
    private final Set<String> tags;
    private final Boolean completed;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Boolean includeFutureTasks;

    private TaskFilter(Builder builder) {
        this.userId = builder.userId;
        this.tags = builder.tags;
        this.completed = builder.completed;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.includeFutureTasks = builder.includeFutureTasks;
    }

    public String getUserId() {
        return userId;
    }

    public Set<String> getTags() {
        return tags != null ? Collections.unmodifiableSet(tags) : null;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Boolean getIncludeFutureTasks() {
        return includeFutureTasks;
    }

    // Builder pattern for flexible filter creation
    public static class Builder {
        private String userId;
        private Set<String> tags;
        private Boolean completed;
        private LocalDate startDate;
        private LocalDate endDate;
        private Boolean includeFutureTasks = false;

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder tags(Set<String> tags) {
            this.tags = tags != null ? new HashSet<>(tags) : null;
            return this;
        }

        public Builder completed(Boolean completed) {
            this.completed = completed;
            return this;
        }

        public Builder dateRange(LocalDate startDate, LocalDate endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
            return this;
        }

        public Builder includeFutureTasks(Boolean includeFutureTasks) {
            this.includeFutureTasks = includeFutureTasks;
            return this;
        }

        public TaskFilter build() {
            return new TaskFilter(this);
        }
    }
}
