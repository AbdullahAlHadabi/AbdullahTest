import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class StudentGradeSystem {
    private static ArrayList<Scholar> students = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    static int studentIdCounter = 1;

    public static void main(String[] args) {
        System.out.println("=== Student Grade Management System ===");
        System.out.println("Built with OOP Principles\n");

        // Load students from file
        loadStudentsFromFile();

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
                    addGrades();
                    break;
                case 4:
                    viewStudentGrades();
                    break;
                case 5:
                    calculateGPA();
                    break;
                case 6:
                    generateReport();
                    break;
                case 7:
                    saveStudentsToFile();
                    System.out.println("Thank you for using Student Grade System!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n=== Student Grade System Menu ===");
        System.out.println("1. Add New Student");
        System.out.println("2. View All Students");
        System.out.println("3. Add Grades for Student");
        System.out.println("4. View Student Grades");
        System.out.println("5. Calculate GPA");
        System.out.println("6. Generate Class Report");
        System.out.println("7. Exit");
        System.out.println("=================================");
    }

    private static void addStudent() {
        System.out.println("\n=== Add New Student ===");
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();

        System.out.print("Enter student email: ");
        String email = scanner.nextLine();

        Scholar student = new Scholar(name, email);
        students.add(student);
        System.out.println("Student added successfully! ID: " + student.getId());
    }

    private static void viewAllStudents() {
        System.out.println("\n=== All Students ===");
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        for (Scholar student : students) {
            System.out.println("ID: " + student.getId() + " | Name: " + student.getName() +
                             " | Email: " + student.getEmail() + " | GPA: " + String.format("%.2f", student.calculateGPA()));
        }
    }

    private static void addGrades() {
        System.out.println("\n=== Add Grades ===");
        System.out.print("Enter student ID: ");
        int studentId = getIntInput("");

        Scholar student = findStudentById(studentId);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("Adding grades for: " + student.getName());
        System.out.print("Enter subject name: ");
        String subject = scanner.nextLine();

        double grade = getDoubleInput("Enter grade (0-100): ");
        while (grade < 0 || grade > 100) {
            System.out.println("Grade must be between 0 and 100.");
            grade = getDoubleInput("Enter grade (0-100): ");
        }

        student.addGrade(subject, grade);
        System.out.println("Grade added successfully!");
    }

    private static void viewStudentGrades() {
        System.out.println("\n=== View Student Grades ===");
        System.out.print("Enter student ID: ");
        int studentId = getIntInput("");

        Scholar student = findStudentById(studentId);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        student.displayGrades();
    }

    private static void calculateGPA() {
        System.out.println("\n=== Calculate GPA ===");
        System.out.print("Enter student ID: ");
        int studentId = getIntInput("");

        Scholar student = findStudentById(studentId);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        double gpa = student.calculateGPA();
        System.out.println("Student: " + student.getName());
        System.out.println("GPA: " + String.format("%.2f", gpa));
        System.out.println("Grade: " + student.getGradeLetter());
    }

    private static void generateReport() {
        System.out.println("\n=== Class Report ===");
        if (students.isEmpty()) {
            System.out.println("No students in the system.");
            return;
        }

        int totalStudents = students.size();
        double classAverage = 0;
        int excellentCount = 0; // GPA >= 3.5
        int goodCount = 0;      // GPA >= 3.0
        int averageCount = 0;   // GPA >= 2.0
        int poorCount = 0;      // GPA < 2.0

        for (Scholar student : students) {
            double gpa = student.calculateGPA();
            classAverage += gpa;

            if (gpa >= 3.5) excellentCount++;
            else if (gpa >= 3.0) goodCount++;
            else if (gpa >= 2.0) averageCount++;
            else poorCount++;
        }

        classAverage /= totalStudents;

        System.out.println("Total Students: " + totalStudents);
        System.out.println("Class Average GPA: " + String.format("%.2f", classAverage));
        System.out.println("\nGrade Distribution:");
        System.out.println("Excellent (A, 3.5+): " + excellentCount + " students");
        System.out.println("Good (B, 3.0-3.49): " + goodCount + " students");
        System.out.println("Average (C, 2.0-2.99): " + averageCount + " students");
        System.out.println("Poor (D/F, <2.0): " + poorCount + " students");
    }

    private static Scholar findStudentById(int id) {
        for (Scholar student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    private static void saveStudentsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("students.txt"))) {
            for (Scholar student : students) {
                writer.println(student.getId() + "," + student.getName() + "," + student.getEmail());
                // Save grades
                for (Grade grade : student.getGrades()) {
                    writer.println("GRADE," + student.getId() + "," + grade.getSubject() + "," + grade.getScore());
                }
            }
            System.out.println("Students saved to file successfully!");
        } catch (IOException e) {
            System.out.println("Error saving students to file: " + e.getMessage());
        }
    }

    private static void loadStudentsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("students.txt"))) {
            String line;
            Scholar currentStudent = null;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3 && !parts[0].equals("GRADE")) {
                    // Student line
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String email = parts[2];

                    currentStudent = new Scholar(name, email);
                    currentStudent.setId(id);
                    students.add(currentStudent);

                    if (id >= studentIdCounter) {
                        studentIdCounter = id + 1;
                    }
                } else if (parts.length >= 4 && parts[0].equals("GRADE") && currentStudent != null) {
                    // Grade line
                    String subject = parts[2];
                    double score = Double.parseDouble(parts[3]);
                    currentStudent.addGrade(subject, score);
                }
            }
            System.out.println("Students loaded from file successfully!");
        } catch (FileNotFoundException e) {
            System.out.println("No saved students file found. Starting with empty system.");
        } catch (IOException e) {
            System.out.println("Error loading students from file: " + e.getMessage());
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

// Scholar class demonstrating encapsulation
class Scholar {
    private int id;
    private String name;
    private String email;
    private ArrayList<Grade> grades;

    public Scholar(String name, String email) {
        this.id = StudentGradeSystem.studentIdCounter++;
        this.name = name;
        this.email = email;
        this.grades = new ArrayList<>();
    }

    public void addGrade(String subject, double score) {
        grades.add(new Grade(subject, score));
    }

    public void displayGrades() {
        System.out.println("\nGrades for " + name + ":");
        if (grades.isEmpty()) {
            System.out.println("No grades recorded.");
            return;
        }

        for (Grade grade : grades) {
            System.out.println(grade.getSubject() + ": " + grade.getScore() + " (" + grade.getLetterGrade() + ")");
        }
        System.out.println("GPA: " + String.format("%.2f", calculateGPA()));
    }

    public double calculateGPA() {
        if (grades.isEmpty()) return 0.0;

        double totalPoints = 0;
        for (Grade grade : grades) {
            totalPoints += grade.getGradePoints();
        }
        return totalPoints / grades.size();
    }

    public String getGradeLetter() {
        double gpa = calculateGPA();
        if (gpa >= 4.0) return "A+";
        else if (gpa >= 3.7) return "A";
        else if (gpa >= 3.3) return "A-";
        else if (gpa >= 3.0) return "B+";
        else if (gpa >= 2.7) return "B";
        else if (gpa >= 2.3) return "B-";
        else if (gpa >= 2.0) return "C+";
        else if (gpa >= 1.7) return "C";
        else if (gpa >= 1.3) return "C-";
        else if (gpa >= 1.0) return "D";
        else return "F";
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public ArrayList<Grade> getGrades() { return grades; }
}

// Grade class for individual subject grades
class Grade {
    private String subject;
    private double score;

    public Grade(String subject, double score) {
        this.subject = subject;
        this.score = score;
    }

    public String getLetterGrade() {
        if (score >= 90) return "A";
        else if (score >= 80) return "B";
        else if (score >= 70) return "C";
        else if (score >= 60) return "D";
        else return "F";
    }

    public double getGradePoints() {
        // Convert percentage to 4.0 scale GPA points
        if (score >= 90) return 4.0;
        else if (score >= 85) return 3.7;
        else if (score >= 80) return 3.3;
        else if (score >= 75) return 3.0;
        else if (score >= 70) return 2.7;
        else if (score >= 65) return 2.3;
        else if (score >= 60) return 2.0;
        else if (score >= 55) return 1.7;
        else if (score >= 50) return 1.3;
        else if (score >= 40) return 1.0;
        else return 0.0;
    }

    // Getters
    public String getSubject() { return subject; }
    public double getScore() { return score; }
}
