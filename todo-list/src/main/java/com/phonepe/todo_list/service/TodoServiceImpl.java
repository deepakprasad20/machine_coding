package com.phonepe.todo_list.service;

import com.phonepe.todo_list.model.*;
import com.phonepe.todo_list.repository.TaskRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class TodoServiceImpl implements TodoService {
    private final TaskRepository repository;

    public TodoServiceImpl(TaskRepository repository) {
        this.repository = repository;
    }


    @Override
    public String addTask(String userId, String title, String description, LocalDate deadline, Set<String> tags,
                          LocalDate scheduledDate) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }

        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Task title cannot be null or empty");
        }

        String taskId = UUID.randomUUID().toString();
        Task task = new Task(taskId, title, description, deadline, tags, scheduledDate, userId);
        repository.addTask(task);
        return taskId;
    }

    @Override
    public Task getTask(String taskId) {
        if (taskId == null || taskId.trim().isEmpty()) {
            throw new IllegalArgumentException("Task ID cannot be null or empty");
        }

        Task task = repository.getTask(taskId);
        if (task == null) {
            throw new IllegalArgumentException("Task with ID " + taskId + " not found");
        }

        return task;
    }

    @Override
    public void modifyTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }

        repository.updateTask(task);
    }

    @Override
    public void removeTask(String taskId) {
        if (taskId == null || taskId.trim().isEmpty()) {
            throw new IllegalArgumentException("Task ID cannot be null or empty");
        }

        repository.removeTask(taskId);
    }

    @Override
    public List<Task> listTasks(TaskFilter filter, TaskSortCriteria sortCriteria) {
        return repository.findTasks(filter, sortCriteria);
    }

    @Override
    public TodoStatistics getStatistics(String userId, LocalDateTime startTime, LocalDateTime endTime) {
        return repository.getStatistics(userId, startTime, endTime);
    }

    @Override
    public List<TodoLog> getActivityLog(String userId, LocalDateTime startTime, LocalDateTime endTime) {
        return repository.getActivityLogs(userId, startTime, endTime);
    }

    @Override
    public void completeTask(String taskId) {
        if (taskId == null || taskId.trim().isEmpty()) {
            throw new IllegalArgumentException("Task ID cannot be null or empty");
        }

        Task task = repository.getTask(taskId);
        if (task == null) {
            throw new IllegalArgumentException("Task with ID " + taskId + " not found");
        }

        task.setCompleted(true);
        repository.updateTask(task);
    }
}
