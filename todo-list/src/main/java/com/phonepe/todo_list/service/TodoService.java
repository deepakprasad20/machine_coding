package com.phonepe.todo_list.service;

import com.phonepe.todo_list.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface TodoService {
    String addTask(String userId, String title, String description, LocalDate deadline,
                   Set<String> tags, LocalDate scheduledDate);
    Task getTask(String taskId);
    void modifyTask(Task task);
    void removeTask(String taskId);
    List<Task> listTasks(TaskFilter filter, TaskSortCriteria sortCriteria);
    TodoStatistics getStatistics(String userId, LocalDateTime startTime, LocalDateTime endTime);
    List<TodoLog> getActivityLog(String userId, LocalDateTime startTime, LocalDateTime endTime);
    void completeTask(String taskId);

}
