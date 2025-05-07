package com.phonepe.todo_list.repository;

import com.phonepe.todo_list.model.*;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository {
    void addTask(Task task);

    Task getTask(String taskId);
    void updateTask(Task task);
    void removeTask(String taskId);
    List<Task> findTasks(TaskFilter filter, TaskSortCriteria sortCriteria);
    List<TodoLog> getActivityLogs(String userId, LocalDateTime startTime, LocalDateTime endTime);
    TodoStatistics getStatistics(String userId, LocalDateTime startTime, LocalDateTime endTime);

}
