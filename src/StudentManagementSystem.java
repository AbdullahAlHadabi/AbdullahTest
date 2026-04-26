import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class StudentManagementSystem {
    private static ArrayList<Students> students = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static final String DATA_FILE = "students_data.txt";
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        System.out.println("=== Advanced Student Management System ===");
        System.out.println("Built with OOP Principles: Inheritance, Encapsulation, Polymorphism");
        System.out.println("Features: File I/O, Sorting, Advanced Statistics, Validation\n");

        // Load existing data
        loadStudentData();

        // Add some sample data if file is empty
        if (students.isEmpty()) {
            initializeSampleData();
        }

        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewAllStudents();
                    break;
                case 3:
                    searchStudent();
                    break;
                case 4:
                    updateStudent();
                    break;
                case 5:
                    deleteStudent();
                    break;
                case 6:
                    displayStatistics();
                    break;
                case 7:
                    sortStudents();
                    break;
                case 8:
                    exportData();
                    break;
                case 9:
                    importData();
                    break;
                case 10:
                    backupData();
                    break;
                case 11:
                    System.out.println("Thank you for using Advanced Student Management System!");
                    saveStudentData(); // Auto-save on exit
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n=== Advanced Main Menu ===");
        System.out.println("1. Add New Student");
        System.out.println("2. View All Students");
        System.out.println("3. Search Student");
        System.out.println("4. Update Student Information");
        System.out.println("5. Delete Student");
        System.out.println("6. Display Advanced Statistics");
        System.out.println("7. Sort Students");
        System.out.println("8. Export Data to CSV");
        System.out.println("9. Import Data from CSV");
        System.out.println("10. Create Backup");
        System.out.println("11. Exit (Auto-save)");
        System.out.println("============================");
    }

    private static void initializeSampleData() {
        // Add sample undergraduate students
        UndergraduateStudent undergrad1 = new UndergraduateStudent("John Doe", 20, "Computer Science", 3.5, "john@email.com", "Spring 2024");
        UndergraduateStudent undergrad2 = new UndergraduateStudent("Jane Smith", 19, "Engineering", 3.8, "jane@email.com", "Fall 2024");

        // Add sample graduate students
        GraduateStudent grad1 = new GraduateStudent("Bob Johnson", 25, "Data Science", 3.9, "bob@email.com", "Dr. Smith", "Machine Learning");
        GraduateStudent grad2 = new GraduateStudent("Alice Brown", 24, "AI Research", 4.0, "alice@email.com", "Dr. Davis", "Neural Networks");

        students.add(undergrad1);
        students.add(undergrad2);
        students.add(grad1);
        students.add(grad2);

        System.out.println("Sample data initialized with 4 students.");
    }

    private static void addStudent() {
        System.out.println("\n=== Add New Student ===");
        System.out.println("1. Undergraduate Student");
        System.out.println("2. Graduate Student");

        int type = getIntInput("Enter student type: ");

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        int age = getIntInput("Enter age: ");

        System.out.print("Enter major: ");
        String major = scanner.nextLine();

        double gpa = getDoubleInput("Enter GPA: ");

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        if (type == 1) {
            System.out.print("Enter semester: ");
            String semester = scanner.nextLine();

            UndergraduateStudent student = new UndergraduateStudent(name, age, major, gpa, email, semester);
            students.add(student);
            System.out.println("Undergraduate student added successfully!");
        } else if (type == 2) {
            System.out.print("Enter advisor name: ");
            String advisor = scanner.nextLine();

            System.out.print("Enter research area: ");
            String researchArea = scanner.nextLine();

            GraduateStudent student = new GraduateStudent(name, age, major, gpa, email, advisor, researchArea);
            students.add(student);
            System.out.println("Graduate student added successfully!");
        } else {
            System.out.println("Invalid student type.");
        }
    }

    private static void viewAllStudents() {
        System.out.println("\n=== All Students ===");
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        for (int i = 0; i < students.size(); i++) {
            System.out.println("\n--- Student " + (i + 1) + " ---");
            students.get(i).displayInfo();
        }
    }

    private static void searchStudent() {
        System.out.println("\n=== Search Student ===");
        System.out.print("Enter student name to search: ");
        String searchName = scanner.nextLine().toLowerCase();

        boolean found = false;
        for (Students student : students) {
            if (student.getName().toLowerCase().contains(searchName)) {
                student.displayInfo();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No student found with name containing: " + searchName);
        }
    }

    private static void updateStudent() {
        System.out.println("\n=== Update Student ===");
        System.out.print("Enter student name to update: ");
        String searchName = scanner.nextLine().toLowerCase();

        for (Students student : students) {
            if (student.getName().toLowerCase().contains(searchName)) {
                System.out.println("Found student: " + student.getName());
                System.out.println("What would you like to update?");
                System.out.println("1. GPA");
                System.out.println("2. Email");
                System.out.println("3. Major");

                int choice = getIntInput("Enter choice: ");

                switch (choice) {
                    case 1:
                        double newGpa = getDoubleInput("Enter new GPA: ");
                        student.setGpa(newGpa);
                        System.out.println("GPA updated successfully!");
                        break;
                    case 2:
                        System.out.print("Enter new email: ");
                        String newEmail = scanner.nextLine();
                        student.setEmail(newEmail);
                        System.out.println("Email updated successfully!");
                        break;
                    case 3:
                        System.out.print("Enter new major: ");
                        String newMajor = scanner.nextLine();
                        student.setMajor(newMajor);
                        System.out.println("Major updated successfully!");
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
                return;
            }
        }
        System.out.println("Student not found.");
    }

    private static void deleteStudent() {
        System.out.println("\n=== Delete Student ===");
        System.out.print("Enter student name to delete: ");
        String searchName = scanner.nextLine().toLowerCase();

        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getName().toLowerCase().contains(searchName)) {
                System.out.println("Found student: " + students.get(i).getName());
                System.out.print("Are you sure you want to delete? (y/n): ");
                String confirm = scanner.nextLine().toLowerCase();

                if (confirm.equals("y") || confirm.equals("yes")) {
                    students.remove(i);
                    System.out.println("Student deleted successfully!");
                } else {
                    System.out.println("Deletion cancelled.");
                }
                return;
            }
        }
        System.out.println("Student not found.");
    }

    private static void displayStatistics() {
        System.out.println("\n=== Advanced System Statistics ===");
        System.out.println("Total Students: " + students.size());

        int undergradCount = 0;
        int gradCount = 0;
        double totalGpa = 0;
        double highestGpa = Double.MIN_VALUE;
        double lowestGpa = Double.MAX_VALUE;
        Students topStudent = null;
        Students lowestStudent = null;

        for (Students student : students) {
            if (student instanceof UndergraduateStudent) {
                undergradCount++;
            } else if (student instanceof GraduateStudent) {
                gradCount++;
            }
            totalGpa += student.getGpa();

            if (student.getGpa() > highestGpa) {
                highestGpa = student.getGpa();
                topStudent = student;
            }
            if (student.getGpa() < lowestGpa) {
                lowestGpa = student.getGpa();
                lowestStudent = student;
            }
        }

        System.out.println("Undergraduate Students: " + undergradCount);
        System.out.println("Graduate Students: " + gradCount);

        if (!students.isEmpty()) {
            double avgGpa = totalGpa / students.size();
            System.out.println("Average GPA: " + String.format("%.2f", avgGpa));
            System.out.println("Highest GPA: " + String.format("%.2f", highestGpa) + " (" + (topStudent != null ? topStudent.getName() : "N/A") + ")");
            System.out.println("Lowest GPA: " + String.format("%.2f", lowestGpa) + " (" + (lowestStudent != null ? lowestStudent.getName() : "N/A") + ")");
        }

        // GPA Distribution
        int excellent = 0, good = 0, average = 0, poor = 0;
        for (Students student : students) {
            double gpa = student.getGpa();
            if (gpa >= 3.5) excellent++;
            else if (gpa >= 3.0) good++;
            else if (gpa >= 2.0) average++;
            else poor++;
        }

        System.out.println("\nGPA Distribution:");
        System.out.println("Excellent (3.5+): " + excellent + " students");
        System.out.println("Good (3.0-3.4): " + good + " students");
        System.out.println("Average (2.0-2.9): " + average + " students");
        System.out.println("Poor (<2.0): " + poor + " students");

        // Age statistics
        if (!students.isEmpty()) {
            int totalAge = 0;
            int minAge = Integer.MAX_VALUE;
            int maxAge = Integer.MIN_VALUE;

            for (Students student : students) {
                totalAge += student.getAge();
                minAge = Math.min(minAge, student.getAge());
                maxAge = Math.max(maxAge, student.getAge());
            }

            double avgAge = (double) totalAge / students.size();
            System.out.println("\nAge Statistics:");
            System.out.println("Average Age: " + String.format("%.1f", avgAge) + " years");
            System.out.println("Youngest: " + minAge + " years");
            System.out.println("Oldest: " + maxAge + " years");
        }
    }

    private static void sortStudents() {
        System.out.println("\n=== Sort Students ===");
        System.out.println("1. By Name");
        System.out.println("2. By GPA");
        System.out.println("3. By Major");

        int choice = getIntInput("Select sorting criteria: ");

        Comparator<Students> comparator = null;
        switch (choice) {
            case 1:
                comparator = Comparator.comparing(Students::getName);
                break;
            case 2:
                comparator = Comparator.comparingDouble(Students::getGpa);
                break;
            case 3:
                comparator = Comparator.comparing(Students::getMajor);
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        Collections.sort(students, comparator);
        System.out.println("Students sorted successfully!");
    }

    private static void exportData() {
        System.out.println("\n=== Export Data to CSV ===");
        System.out.print("Enter file name (without extension): ");
        String fileName = scanner.nextLine().trim();

        if (fileName.isEmpty()) {
            System.out.println("File name cannot be empty.");
            return;
        }

        File file = new File(fileName + ".csv");
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(file))) {
            writer.println("Name,Age,Major,GPA,Email,Type,Advisor,ResearchArea,Semester");
            for (Students student : students) {
                writer.print(student.getName() + ",");
                writer.print(student.getAge() + ",");
                writer.print(student.getMajor() + ",");
                writer.print(student.getGpa() + ",");
                writer.print(student.getEmail() + ",");
                if (student instanceof UndergraduateStudent) {
                    writer.print("Undergraduate,,," + ((UndergraduateStudent) student).getCurrentSemester());
                } else if (student instanceof GraduateStudent) {
                    writer.print("Graduate," + ((GraduateStudent) student).getAdvisorName() + "," + ((GraduateStudent) student).getResearchArea() + ",");
                }
                writer.println();
            }
            System.out.println("Data exported successfully to " + file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error exporting data: " + e.getMessage());
        }
    }

    private static void importData() {
        System.out.println("\n=== Import Data from CSV ===");
        System.out.print("Enter file name (without extension): ");
        String fileName = scanner.nextLine().trim();

        if (fileName.isEmpty()) {
            System.out.println("File name cannot be empty.");
            return;
        }

        File file = new File(fileName + ".csv");
        if (!file.exists()) {
            System.out.println("File not found: " + file.getAbsolutePath());
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            reader.readLine(); // Skip header line
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 5) continue; // Incomplete data

                String name = data[0];
                int age = Integer.parseInt(data[1]);
                String major = data[2];
                double gpa = Double.parseDouble(data[3]);
                String email = data[4];

                Students student;
                if (data.length == 8) { // Graduate student
                    String advisor = data[5];
                    String researchArea = data[6];
                    student = new GraduateStudent(name, age, major, gpa, email, advisor, researchArea);
                } else if (data.length == 7) { // Undergraduate student
                    String semester = data[5];
                    student = new UndergraduateStudent(name, age, major, gpa, email, semester);
                } else {
                    continue; // Unknown format
                }
                students.add(student);
            }
            System.out.println("Data imported successfully from " + file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error importing data: " + e.getMessage());
        }
    }

    private static void backupData() {
        System.out.println("\n=== Create Backup ===");
        String timestamp = LocalDateTime.now().format(formatter);
        String backupFileName = "backup_" + timestamp + ".txt";

        try (PrintWriter writer = new PrintWriter(new FileOutputStream(backupFileName))) {
            for (Students student : students) {
                writer.println(student.getName() + "|" + student.getAge() + "|" + student.getMajor() + "|" + student.getGpa() + "|" + student.getEmail());
            }
            System.out.println("Backup created successfully: " + backupFileName);
        } catch (IOException e) {
            System.out.println("Error creating backup: " + e.getMessage());
        }
    }

    private static void loadStudentData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            reader.readLine(); // Skip header line
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 5) continue; // Incomplete data

                String name = data[0];
                int age = Integer.parseInt(data[1]);
                String major = data[2];
                double gpa = Double.parseDouble(data[3]);
                String email = data[4];

                Students student;
                if (data.length == 8) { // Graduate student
                    String advisor = data[5];
                    String researchArea = data[6];
                    student = new GraduateStudent(name, age, major, gpa, email, advisor, researchArea);
                } else if (data.length == 7) { // Undergraduate student
                    String semester = data[5];
                    student = new UndergraduateStudent(name, age, major, gpa, email, semester);
                } else {
                    continue; // Unknown format
                }
                students.add(student);
            }
            System.out.println("Student data loaded from " + file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error loading student data: " + e.getMessage());
        }
    }

    private static void saveStudentData() {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(DATA_FILE))) {
            writer.println("Name,Age,Major,GPA,Email,Type,Advisor,ResearchArea,Semester");
            for (Students student : students) {
                writer.print(student.getName() + ",");
                writer.print(student.getAge() + ",");
                writer.print(student.getMajor() + ",");
                writer.print(student.getGpa() + ",");
                writer.print(student.getEmail() + ",");
                if (student instanceof UndergraduateStudent) {
                    writer.print("Undergraduate,,," + ((UndergraduateStudent) student).getCurrentSemester());
                } else if (student instanceof GraduateStudent) {
                    writer.print("Graduate," + ((GraduateStudent) student).getAdvisorName() + "," + ((GraduateStudent) student).getResearchArea() + ",");
                }
                writer.println();
            }
            System.out.println("Student data saved to " + DATA_FILE);
        } catch (IOException e) {
            System.out.println("Error saving student data: " + e.getMessage());
        }
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = Double.parseDouble(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}

// Abstract base class demonstrating inheritance
abstract class Students {
    private String name;
    private int age;
    private String major;
    private double gpa;
    private String email;

    public Students(String name, int age, String major, double gpa, String email) {
        this.name = name;
        this.age = age;
        this.major = major;
        this.gpa = gpa;
        this.email = email;
    }

    // Abstract method - must be implemented by subclasses
    public abstract void displayInfo();

    // Getters and setters demonstrating encapsulation
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }

    public double getGpa() { return gpa; }
    public void setGpa(double gpa) { this.gpa = gpa; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}

// Undergraduate student class demonstrating inheritance
class UndergraduateStudent extends Students {
    private String currentSemester;

    public UndergraduateStudent(String name, int age, String major, double gpa, String email, String currentSemester) {
        super(name, age, major, gpa, email); // Call parent constructor
        this.currentSemester = currentSemester;
    }

    @Override
    public void displayInfo() {
        System.out.println("Student Type: Undergraduate");
        System.out.println("Name: " + getName());
        System.out.println("Age: " + getAge());
        System.out.println("Major: " + getMajor());
        System.out.println("GPA: " + getGpa());
        System.out.println("Email: " + getEmail());
        System.out.println("Current Semester: " + currentSemester);
        System.out.println("Academic Status: " + getAcademicStatus());
    }

    public String getCurrentSemester() { return currentSemester; }
    public void setCurrentSemester(String currentSemester) { this.currentSemester = currentSemester; }

    // Method specific to undergraduate students
    public String getAcademicStatus() {
        if (getGpa() >= 3.5) return "Dean's List";
        else if (getGpa() >= 3.0) return "Good Standing";
        else if (getGpa() >= 2.0) return "Academic Warning";
        else return "Academic Probation";
    }
}

// Graduate student class demonstrating inheritance
class GraduateStudent extends Students {
    private String advisorName;
    private String researchArea;

    public GraduateStudent(String name, int age, String major, double gpa, String email, String advisorName, String researchArea) {
        super(name, age, major, gpa, email); // Call parent constructor
        this.advisorName = advisorName;
        this.researchArea = researchArea;
    }

    @Override
    public void displayInfo() {
        System.out.println("Student Type: Graduate");
        System.out.println("Name: " + getName());
        System.out.println("Age: " + getAge());
        System.out.println("Major: " + getMajor());
        System.out.println("GPA: " + getGpa());
        System.out.println("Email: " + getEmail());
        System.out.println("Advisor: " + advisorName);
        System.out.println("Research Area: " + researchArea);
        System.out.println("Funding Status: " + getFundingStatus());
    }

    public String getAdvisorName() { return advisorName; }
    public void setAdvisorName(String advisorName) { this.advisorName = advisorName; }

    public String getResearchArea() { return researchArea; }
    public void setResearchArea(String researchArea) { this.researchArea = researchArea; }

    // Method specific to graduate students
    public String getFundingStatus() {
        if (getGpa() >= 3.8) return "Full Fellowship";
        else if (getGpa() >= 3.5) return "Partial Funding";
        else return "Self-funded";
    }
}
