package com.phonepe.todo_list.model;

import java.time.LocalDateTime;

public class TodoLog {
    private final String taskId;
    private final TaskType taskType;
    private final LocalDateTime timestamp;
    private final String userId;
    private final String details;


    public TodoLog(String taskId, TaskType taskType, LocalDateTime timestamp,
                   String userId, String details) {
        this.taskId = taskId;
        this.taskType = taskType;
        this.timestamp = timestamp;
        this.userId = userId;
        this.details = details;
    }

    public String getTaskId() {
        return taskId;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public String getDetails() {
        return details;
    }

    @Override
    public String toString() {
        return String.format("User '%s' %s task '%s' at %s: %s",
                userId, taskType.toString().toLowerCase(), taskId, timestamp, details);
    }
}
