package org.example;

public class TaskManagement {
    public static void main(String[] args) {
        // Admin credentials
        Admin admin = new Admin("mehdiaititto239@gmail.com", "mkzf rekq itxg ukgm");

        // User credentials
        User user = new User("mehdirajawi239@gmail.com", "ztzf kapd alhk tnmu");

        // Create and assign a task
        Task task = new Task("Complete Report", "Prepare the Sprint 1 & 2 report for tomorrow.");
        admin.assignTask(task, user);

        // User retrieves tasks
        System.out.println("\nRetrieving tasks for user...");
        user.listTasks();
    }
}