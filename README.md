# Advanced Task Manager

A comprehensive Java application for managing tasks with full CRUD operations, built using Object-Oriented Programming principles.

## Features

### Core Functionality
- ✅ **Add Tasks**: Create new tasks with title, description, priority, and category
- ✅ **View Tasks**: Display all tasks with detailed information
- ✅ **Mark Complete**: Update task status to completed
- ✅ **Delete Tasks**: Remove tasks from the system
- ✅ **Search Tasks**: Find tasks by title or description
- ✅ **Sort Tasks**: Organize tasks by priority, category, status, or date
- ✅ **Statistics**: View comprehensive task analytics

### Advanced Features
- 🔄 **Persistent Storage**: Tasks are automatically saved to file and loaded on startup
- 📊 **Data Analytics**: Detailed statistics on task completion, priorities, and categories
- 🔍 **Advanced Search**: Case-insensitive search through task titles and descriptions
- 📅 **Time Tracking**: Creation and completion timestamps for all tasks
- 🎯 **Priority System**: Three-level priority system (Low, Medium, High)
- 📂 **Category Organization**: Four predefined categories (Work, Personal, Study, Health)

## OOP Concepts Demonstrated

### 1. **Encapsulation**
- Private fields in `Task` class
- Public getter methods for controlled access
- Data validation and business logic encapsulation

### 2. **Inheritance**
- `Task` class serves as the base class
- Potential for extending with specialized task types

### 3. **Polymorphism**
- Method overriding in display functionality
- Flexible sorting with different comparators

### 4. **Abstraction**
- Abstract data management through ArrayList
- Simplified interface hiding complex operations

## Technical Architecture

### Class Structure
```
TaskManager (Main Class)
├── ArrayList<Task> tasks
├── Scanner scanner
└── Static methods for all operations

Task (Data Class)
├── Private fields for all task properties
├── Constructor for initialization
├── Getters for data access
└── Business logic methods

Enums
├── Priority (LOW, MEDIUM, HIGH)
└── Category (WORK, PERSONAL, STUDY, HEALTH)
```

### File Structure
```
AbdullahTest/
├── src/
│   └── TaskManager.java
├── tasks.txt (auto-generated)
└── README.md
```

## How to Run

### Prerequisites
- Java 8 or higher installed
- Command line access

### Execution Steps
1. **Compile the program:**
   ```bash
   javac -d . src/TaskManager.java
   ```

2. **Run the application:**
   ```bash
   java TaskManager
   ```

### Sample Usage
```
=== Advanced Task Manager ===
Built with OOP Principles

1. Add New Task
2. View All Tasks
3. Mark Task Complete
4. Delete Task
5. Search Tasks
6. Sort Tasks
7. Display Statistics
8. Exit
```

## Data Persistence

Tasks are automatically saved to `tasks.txt` when you exit the application and loaded when you start it again. The file format is CSV with the following structure:

```
id,title,description,priority,category,completed,createdDate,completedDate
```

## Future Enhancements

- [ ] GUI interface using JavaFX or Swing
- [ ] Database integration (SQLite, MySQL)
- [ ] Task reminders and notifications
- [ ] Export to different formats (JSON, XML, PDF)
- [ ] User authentication and multi-user support
- [ ] Task templates and recurring tasks
- [ ] Advanced filtering and reporting

## Learning Outcomes

This project demonstrates:
- ✅ Advanced Java programming concepts
- ✅ File I/O operations
- ✅ Data structures and algorithms
- ✅ Error handling and validation
- ✅ Code organization and documentation
- ✅ Real-world application development

## Author

Built as part of Java OOP learning journey.

---

**Note**: This application is designed for educational purposes and demonstrates best practices in Java development.
