package com.phonepe.todo_list;


import com.phonepe.todo_list.model.*;
import com.phonepe.todo_list.repository.TaskRepository;
import com.phonepe.todo_list.repository.TaskRepositoryImpl;
import com.phonepe.todo_list.service.TodoService;
import com.phonepe.todo_list.service.TodoServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class TodoListApplication {

	public static void main(String[] args) {
		TaskRepository repository = new TaskRepositoryImpl();
		TodoService todoService = new TodoServiceImpl(repository);


		String user1 = "Deepak";
		String user2 = "someRandomName";


		System.out.println("=== Adding Tasks ===");
		String task1Id = todoService.addTask(
				user1,
				"Complete TODO LIST Application",
				"this is an application to add todo list",
				LocalDate.now().plusDays(3),
				Set.of("Interview", "urgent"),
				LocalDate.now()
		);
		System.out.println("Added task: " + todoService.getTask(task1Id));

		String task2Id = todoService.addTask(
				user1,
				"complete System design course",
				"You have to complete system design course by some xyz",
				LocalDate.now().plusDays(1),
				Set.of("Study", "work"),
				LocalDate.now()
		);
		System.out.println("Added task: " + todoService.getTask(task2Id));

		String task3Id = todoService.addTask(
				user2,
				"Schedule meeting",
				"Set up team meeting for next sprint",
				LocalDate.now().plusDays(2),
				Set.of("work", "meeting"),
				LocalDate.now()
		);
		System.out.println("Added task: " + todoService.getTask(task3Id));

		// Future task
		String task4Id = todoService.addTask(
				user1,
				"Plan vacation",
				"Visit chikmangalur",
				LocalDate.now().plusDays(30),
				Set.of("personal", "vacation"),
				LocalDate.now().plusDays(10)
		);
		System.out.println("Added future task: " + todoService.getTask(task4Id));

		// Task listing
		System.out.println("\n=== Listing Tasks for Deepak ===");
		TaskFilter filter = new TaskFilter.Builder()
				.userId(user1)
				.build();
		List<Task> user1Tasks = todoService.listTasks(filter, TaskSortCriteria.DEADLINE_ASC);
		user1Tasks.forEach(System.out::println);

		// task Filtering
		System.out.println("\n=== Listing Personal Tasks for Deepak ===");
		filter = new TaskFilter.Builder()
				.userId(user1)
				.tags(Set.of("personal"))
				.build();
		List<Task> user1PersonalTasks = todoService.listTasks(filter, TaskSortCriteria.DEADLINE_ASC);
		user1PersonalTasks.forEach(System.out::println);

		// Demonstrate filtering future tasks
		System.out.println("\n=== Listing Future Tasks for Deepak ===");
		filter = new TaskFilter.Builder()
				.userId(user1)
				.includeFutureTasks(true)
				.build();
		List<Task> user1FutureTasks = todoService.listTasks(filter, TaskSortCriteria.DEADLINE_ASC);
		user1FutureTasks.forEach(System.out::println);

		// Demonstrate modifying a task
		System.out.println("\n=== Modifying Task ===");
		Task task1 = todoService.getTask(task1Id);
		task1.setTitle("Updated task");
		task1.setDescription("Updated description");
		todoService.modifyTask(task1);
		System.out.println("Modified task: " + todoService.getTask(task1Id));

		// task 2 completing
		System.out.println("\n=== Completing Task ===");
		todoService.completeTask(task2Id);

		// get the completed task. It will not be there in todo list as we need to remove it.
		try {
			Task completedTask = todoService.getTask(task2Id);
			System.out.println("This should not print as task should be removed: " + completedTask);
		} catch (IllegalArgumentException e) {
			System.out.println("Expected error: " + e.getMessage());
		}

		// call removing a task
		System.out.println("\n=== Removing Task ===");
		todoService.removeTask(task3Id);

		// Try to access removed task
		try {
			Task removedTask = todoService.getTask(task3Id);
			System.out.println("This should not print as task should be removed: " + removedTask);
		} catch (IllegalArgumentException e) {
			System.out.println("Expected error: " + e.getMessage());
		}

		todoService.completeTask(task4Id);

		// show activity log
		System.out.println("\n=== Activity Log for " + user1 +  " ===");
		List<TodoLog> activityLogs = todoService.getActivityLog(user1, null, null);
		activityLogs.forEach(System.out::println);

		// show statistics
		System.out.println("\n=== Statistics for " + user1+  " ===");
		TodoStatistics statistics = todoService.getStatistics(user1, null, null);
		System.out.println(statistics);

		//TODO: Need to write the test cases.
	}

}
