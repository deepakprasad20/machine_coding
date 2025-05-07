package com.phonepe.todo_list.model;

import java.time.LocalDateTime;

public class TodoStatistics {
    private final int tasksAdded;
    private final int tasksCompleted;
    private final int tasksSpilledOverDeadline;
    private final LocalDateTime startPeriod;
    private final LocalDateTime endPeriod;
    private final String userId;

    public TodoStatistics(int tasksAdded, int tasksCompleted, int tasksSpilledOverDeadline,
                          LocalDateTime startPeriod, LocalDateTime endPeriod, String userId) {
        this.tasksAdded = tasksAdded;
        this.tasksCompleted = tasksCompleted;
        this.tasksSpilledOverDeadline = tasksSpilledOverDeadline;
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
        this.userId = userId;
    }

    public int getTasksAdded() {
        return tasksAdded;
    }

    public int getTasksCompleted() {
        return tasksCompleted;
    }

    public int getTasksSpilledOverDeadline() {
        return tasksSpilledOverDeadline;
    }

    public LocalDateTime getStartPeriod() {
        return startPeriod;
    }

    public LocalDateTime getEndPeriod() {
        return endPeriod;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return String.format("Statistics for user '%s' from %s to %s:%n" +
                        "Tasks Added: %d%n" +
                        "Tasks Completed: %d%n" +
                        "Tasks Spilled Over Deadline: %d",
                userId, startPeriod, endPeriod, tasksAdded, tasksCompleted, tasksSpilledOverDeadline);
    }
}
