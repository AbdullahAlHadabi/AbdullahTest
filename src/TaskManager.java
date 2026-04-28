import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TaskManager {
    private static ArrayList<Task> tasks = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static int taskIdCounter = 1;

    public static void main(String[] args) {
        System.out.println("=== Advanced Task Manager ===");
        System.out.println("Built with OOP Principles\n");

        // Add some sample tasks
        initializeSampleTasks();

        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    viewAllTasks();
                    break;
                case 3:
                    markTaskComplete();
                    break;
                case 4:
                    deleteTask();
                    break;
                case 5:
                    searchTasks();
                    break;
                case 6:
                    displayStatistics();
                    break;
                case 7:
                    System.out.println("Thank you for using Task Manager!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n=== Task Manager Menu ===");
        System.out.println("1. Add New Task");
        System.out.println("2. View All Tasks");
        System.out.println("3. Mark Task Complete");
        System.out.println("4. Delete Task");
        System.out.println("5. Search Tasks");
        System.out.println("6. Display Statistics");
        System.out.println("7. Exit");
        System.out.println("=========================");
    }

    private static void initializeSampleTasks() {
        Task task1 = new Task("Complete Java project", "Finish the OOP assignment", Priority.HIGH, Category.WORK);
        Task task2 = new Task("Buy groceries", "Weekly shopping list", Priority.MEDIUM, Category.PERSONAL);
        Task task3 = new Task("Study for exam", "Review OOP concepts", Priority.HIGH, Category.STUDY);

        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);

        System.out.println("Sample tasks initialized.");
    }

    private static void addTask() {
        System.out.println("\n=== Add New Task ===");
        System.out.print("Enter task title: ");
        String title = scanner.nextLine();

        System.out.print("Enter task description: ");
        String description = scanner.nextLine();

        System.out.println("Select priority:");
        System.out.println("1. Low");
        System.out.println("2. Medium");
        System.out.println("3. High");
        int priorityChoice = getIntInput("Enter priority: ");
        Priority priority = Priority.values()[priorityChoice - 1];

        System.out.println("Select category:");
        System.out.println("1. Work");
        System.out.println("2. Personal");
        System.out.println("3. Study");
        System.out.println("4. Health");
        int categoryChoice = getIntInput("Enter category: ");
        Category category = Category.values()[categoryChoice - 1];

        Task newTask = new Task(title, description, priority, category);
        tasks.add(newTask);
        System.out.println("Task added successfully!");
    }

    private static void viewAllTasks() {
        System.out.println("\n=== All Tasks ===");
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }

        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("\n--- Task " + (i + 1) + " ---");
            tasks.get(i).displayTask();
        }
    }

    private static void markTaskComplete() {
        System.out.println("\n=== Mark Task Complete ===");
        System.out.print("Enter task title to mark complete: ");
        String searchTitle = scanner.nextLine().toLowerCase();

        for (Task task : tasks) {
            if (task.getTitle().toLowerCase().contains(searchTitle) && !task.isCompleted()) {
                task.markComplete();
                System.out.println("Task marked as complete!");
                return;
            }
        }
        System.out.println("Task not found or already completed.");
    }

    private static void deleteTask() {
        System.out.println("\n=== Delete Task ===");
        System.out.print("Enter task title to delete: ");
        String searchTitle = scanner.nextLine().toLowerCase();

        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getTitle().toLowerCase().contains(searchTitle)) {
                tasks.remove(i);
                System.out.println("Task deleted successfully!");
                return;
            }
        }
        System.out.println("Task not found.");
    }

    private static void searchTasks() {
        System.out.println("\n=== Search Tasks ===");
        System.out.print("Enter search term: ");
        String searchTerm = scanner.nextLine().toLowerCase();

        boolean found = false;
        for (Task task : tasks) {
            if (task.getTitle().toLowerCase().contains(searchTerm) ||
                task.getDescription().toLowerCase().contains(searchTerm)) {
                task.displayTask();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No tasks found matching: " + searchTerm);
        }
    }

    private static void displayStatistics() {
        System.out.println("\n=== Task Statistics ===");
        System.out.println("Total Tasks: " + tasks.size());

        int completed = 0;
        int[] priorityCount = new int[3]; // LOW, MEDIUM, HIGH
        int[] categoryCount = new int[4]; // WORK, PERSONAL, STUDY, HEALTH

        for (Task task : tasks) {
            if (task.isCompleted()) completed++;

            switch (task.getPriority()) {
                case LOW: priorityCount[0]++; break;
                case MEDIUM: priorityCount[1]++; break;
                case HIGH: priorityCount[2]++; break;
            }

            switch (task.getCategory()) {
                case WORK: categoryCount[0]++; break;
                case PERSONAL: categoryCount[1]++; break;
                case STUDY: categoryCount[2]++; break;
                case HEALTH: categoryCount[3]++; break;
            }
        }

        System.out.println("Completed Tasks: " + completed);
        System.out.println("Pending Tasks: " + (tasks.size() - completed));

        System.out.println("\nPriority Breakdown:");
        System.out.println("Low: " + priorityCount[0]);
        System.out.println("Medium: " + priorityCount[1]);
        System.out.println("High: " + priorityCount[2]);

        System.out.println("\nCategory Breakdown:");
        System.out.println("Work: " + categoryCount[0]);
        System.out.println("Personal: " + categoryCount[1]);
        System.out.println("Study: " + categoryCount[2]);
        System.out.println("Health: " + categoryCount[3]);
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine());
                if (value >= 1 && value <= 4) { // Adjust based on context
                    return value;
                } else {
                    System.out.println("Please enter a number between 1 and 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }
}

// Task class demonstrating encapsulation
class Task {
    private int id;
    private String title;
    private String description;
    private Priority priority;
    private Category category;
    private boolean completed;
    private LocalDateTime createdDate;
    private LocalDateTime completedDate;

    public Task(String title, String description, Priority priority, Category category) {
        this.id = TaskManager.taskIdCounter++;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.category = category;
        this.completed = false;
        this.createdDate = LocalDateTime.now();
    }

    public void displayTask() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        System.out.println("ID: " + id);
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Priority: " + priority);
        System.out.println("Category: " + category);
        System.out.println("Status: " + (completed ? "Completed" : "Pending"));
        System.out.println("Created: " + createdDate.format(formatter));
        if (completed && completedDate != null) {
            System.out.println("Completed: " + completedDate.format(formatter));
        }
    }

    public void markComplete() {
        this.completed = true;
        this.completedDate = LocalDateTime.now();
    }

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Priority getPriority() { return priority; }
    public Category getCategory() { return category; }
    public boolean isCompleted() { return completed; }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public LocalDateTime getCompletedDate() { return completedDate; }
}

// Enums for type safety
enum Priority {
    LOW, MEDIUM, HIGH
}

enum Category {
    WORK, PERSONAL, STUDY, HEALTH
}
