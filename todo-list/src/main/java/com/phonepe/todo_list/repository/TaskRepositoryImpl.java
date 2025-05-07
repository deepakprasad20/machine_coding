package com.phonepe.todo_list.repository;

import com.phonepe.todo_list.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TaskRepositoryImpl implements TaskRepository {

    private final Map<String, Task> tasks = new ConcurrentHashMap<>();
    private final List<TodoLog> todoLogs = new ArrayList<>();

    private final ReadWriteLock tasksLock = new ReentrantReadWriteLock();
    private final ReadWriteLock logsLock = new ReentrantReadWriteLock();


    @Override
    public void addTask(Task task) {
        tasksLock.writeLock().lock();
        try {
            tasks.put(task.getId(), task);

            // Log the activity
            TodoLog log = new TodoLog(
                    task.getId(),
                    TaskType.ADD,
                    LocalDateTime.now(),
                    task.getUserId(),
                    "Added task: " + task.getTitle()
            );

            todoLogs.add(log);
        } finally {
            tasksLock.writeLock().unlock();
        }
    }

    @Override
    public Task getTask(String taskId) {
        tasksLock.readLock().lock();
        try {
            return tasks.get(taskId);
        } finally {
            tasksLock.readLock().unlock();
        }
    }

    @Override
    public void updateTask(Task task) {
        tasksLock.writeLock().lock();
        try {
            Task existingTask = tasks.get(task.getId());
            if (existingTask == null) {
                throw new IllegalArgumentException("Task with ID " + task.getId() + " not found");
            }

            tasks.put(task.getId(), task);

            // Check if the task is completed and remove if necessary
            if (task.isCompleted()) {
                // Log completion
                TodoLog completionLog = new TodoLog(
                        task.getId(),
                        TaskType.COMPLETED,
                        LocalDateTime.now(),
                        task.getUserId(),
                        "Completed task: " + task.getTitle()
                );

                logsLock.writeLock().lock();
                try {
                    todoLogs.add(completionLog);
                } finally {
                    logsLock.writeLock().unlock();
                }

                // Remove the task since it's completed
                tasks.remove(task.getId());
            } else {
                // Log modification
                TodoLog modLog = new TodoLog(
                        task.getId(),
                        TaskType.MODIFY,
                        LocalDateTime.now(),
                        task.getUserId(),
                        "Modified task: " + task.getTitle()
                );

                logsLock.writeLock().lock();
                try {
                    todoLogs.add(modLog);
                } finally {
                    logsLock.writeLock().unlock();
                }
            }
        } finally {
            tasksLock.writeLock().unlock();
        }
    }

    @Override
    public void removeTask(String taskId) {
        Task task = tasks.get(taskId);
        if (task == null) {
            throw new IllegalArgumentException("Task with ID " + taskId + " not found");
        }

        tasks.remove(taskId);

        // Log removal
        TodoLog log = new TodoLog(
                taskId,
                TaskType.REMOVE,
                LocalDateTime.now(),
                task.getUserId(),
                "Removed task: " + task.getTitle()
        );
        todoLogs.add(log);
    }

    @Override
    public List<Task> findTasks(TaskFilter filter, TaskSortCriteria sortCriteria) {
        Stream<Task> taskStream = tasks.values().stream();

        // Apply filters
        if (filter != null) {
            if (filter.getUserId() != null) {
                taskStream = taskStream.filter(task -> task.getUserId().equals(filter.getUserId()));
            }


            if (filter.getTags() != null && !filter.getTags().isEmpty()) {
                taskStream = taskStream.filter(task ->
                        !Collections.disjoint(task.getTags(), filter.getTags()));
            }


            if (filter.getCompleted() != null) {
                taskStream = taskStream.filter(task -> task.isCompleted() == filter.getCompleted());
            }

            // TODO: Filter by date range based on scheduled date
            LocalDate today = LocalDate.now();
            if (!Boolean.TRUE.equals(filter.getIncludeFutureTasks())) {
                taskStream = taskStream.filter(task ->
                        task.getScheduledDate() == null || !task.getScheduledDate().isAfter(today));
            }

            if (filter.getStartDate() != null) {
                taskStream = taskStream.filter(task ->
                        task.getScheduledDate() == null || !task.getScheduledDate().isBefore(filter.getStartDate()));
            }

            if (filter.getEndDate() != null) {
                taskStream = taskStream.filter(task ->
                        task.getScheduledDate() == null || !task.getScheduledDate().isAfter(filter.getEndDate()));
            }
        }


        //TODO: Sort based on sort criteria. will do this based on time remaining.
        if (sortCriteria != null) {
            switch (sortCriteria) {
                case DEADLINE_ASC:
                    taskStream = taskStream.sorted(Comparator.comparing(Task::getDeadline,
                            Comparator.nullsLast(Comparator.naturalOrder())));
                    break;
                case DEADLINE_DESC:
                    taskStream = taskStream.sorted(Comparator.comparing(Task::getDeadline,
                            Comparator.nullsLast(Comparator.naturalOrder())).reversed());
                    break;
                case CREATED_DATE_ASC:
                    taskStream = taskStream.sorted(Comparator.comparing(Task::getCreatedAt));
                    break;
                case CREATED_DATE_DESC:
                    taskStream = taskStream.sorted(Comparator.comparing(Task::getCreatedAt).reversed());
                    break;
                case TITLE_ASC:
                    taskStream = taskStream.sorted(Comparator.comparing(Task::getTitle));
                    break;
                case TITLE_DESC:
                    taskStream = taskStream.sorted(Comparator.comparing(Task::getTitle).reversed());
                    break;
            }
        }

        return taskStream.collect(Collectors.toList());
    }


    @Override
    public TodoStatistics getStatistics(String userId, LocalDateTime startTime, LocalDateTime endTime) {
        List<TodoLog> filteredLogs = getActivityLogs(userId, startTime, endTime);


        long tasksAdded = filteredLogs.stream()
                .filter(log -> log.getTaskType() == TaskType.ADD)
                .count();


        long tasksCompleted = filteredLogs.stream()
                .filter(log -> log.getTaskType() == TaskType.COMPLETED)
                .count();



        int tasksSpilledOverDeadline = 0;

        // Create a map of tasks with their deadlines to check for spillovers
        Map<String, LocalDate> taskDeadlines = new HashMap<>();
        Map<String, LocalDateTime> taskCompletions = new HashMap<>();

        for (TodoLog log : todoLogs) {
            if (log.getTaskType() == TaskType.ADD && log.getUserId().equals(userId)) {
                Task task = getTask(log.getTaskId());
                if (task != null && task.getDeadline() != null) {
                    taskDeadlines.put(log.getTaskId(), task.getDeadline());
                }
            } else if (log.getTaskType() == TaskType.COMPLETED && log.getUserId().equals(userId)) {
                taskCompletions.put(log.getTaskId(), log.getTimestamp());
            }
        }

        // Check for spillovers
        for (Map.Entry<String, LocalDateTime> entry : taskCompletions.entrySet()) {
            String taskId = entry.getKey();
            LocalDateTime completionTime = entry.getValue();

            if (taskDeadlines.containsKey(taskId)) {
                LocalDate deadline = taskDeadlines.get(taskId);
                if (completionTime.toLocalDate().isAfter(deadline)) {
                    tasksSpilledOverDeadline++;
                }
            }
        }

        return new TodoStatistics(
                (int) tasksAdded,
                (int) tasksCompleted,
                tasksSpilledOverDeadline,
                startTime != null ? startTime : filteredLogs.stream()
                        .min(Comparator.comparing(TodoLog::getTimestamp))
                        .map(TodoLog::getTimestamp)
                        .orElse(LocalDateTime.now()),
                endTime != null ? endTime : filteredLogs.stream()
                        .max(Comparator.comparing(TodoLog::getTimestamp))
                        .map(TodoLog::getTimestamp)
                        .orElse(LocalDateTime.now()),
                userId
        );
    }

    @Override
    public List<TodoLog> getActivityLogs(String userId, LocalDateTime startTime, LocalDateTime endTime) {
        Stream<TodoLog> logStream = todoLogs.stream();

        // Filter by user ID
        if (userId != null) {
            logStream = logStream.filter(log -> log.getUserId().equals(userId));
        }

        // Filter by time range
        if (startTime != null) {
            logStream = logStream.filter(log -> !log.getTimestamp().isBefore(startTime));
        }

        if (endTime != null) {
            logStream = logStream.filter(log -> !log.getTimestamp().isAfter(endTime));
        }

        return logStream
                .sorted(Comparator.comparing(TodoLog::getTimestamp))
                .collect(Collectors.toList());
    }
}
