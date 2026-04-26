import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class CourseManagementSystem {
    private static ArrayList<Course> courses = new ArrayList<>();
    private static ArrayList<Enrollment> enrollments = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static final String COURSE_DATA_FILE = "courses_data.txt";
    private static final String ENROLLMENT_DATA_FILE = "enrollments_data.txt";

    public static void main(String[] args) {
        System.out.println("=== Course Management System ===");
        System.out.println("Manage courses, enrollments, and academic records\n");

        // Load existing data
        loadCourseData();
        loadEnrollmentData();

        // Add sample data if empty
        if (courses.isEmpty()) {
            initializeSampleCourses();
        }

        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    addCourse();
                    break;
                case 2:
                    viewAllCourses();
                    break;
                case 3:
                    searchCourse();
                    break;
                case 4:
                    enrollStudent();
                    break;
                case 5:
                    viewEnrollments();
                    break;
                case 6:
                    assignGrade();
                    break;
                case 7:
                    generateTranscript();
                    break;
                case 8:
                    courseStatistics();
                    break;
                case 9:
                    System.out.println("Thank you for using Course Management System!");
                    saveData();
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n=== Course Management Menu ===");
        System.out.println("1. Add New Course");
        System.out.println("2. View All Courses");
        System.out.println("3. Search Course");
        System.out.println("4. Enroll Student in Course");
        System.out.println("5. View Enrollments");
        System.out.println("6. Assign Grade");
        System.out.println("7. Generate Student Transcript");
        System.out.println("8. Course Statistics");
        System.out.println("9. Exit");
        System.out.println("================================");
    }

    private static void initializeSampleCourses() {
        courses.add(new Course("CS101", "Introduction to Programming", "Dr. Smith", 3, "Mon/Wed 10:00-11:30", 30));
        courses.add(new Course("MATH201", "Calculus II", "Dr. Johnson", 4, "Tue/Thu 14:00-15:30", 25));
        courses.add(new Course("ENG101", "English Composition", "Prof. Davis", 3, "Mon/Wed/Fri 9:00-10:00", 35));
        courses.add(new Course("PHYS101", "Physics I", "Dr. Wilson", 4, "Tue/Thu 11:00-12:30", 20));
        System.out.println("Sample courses initialized.");
    }

    private static void addCourse() {
        System.out.println("\n=== Add New Course ===");
        System.out.print("Enter course code: ");
        String code = scanner.nextLine().toUpperCase();

        // Check if course code already exists
        for (Course course : courses) {
            if (course.getCode().equals(code)) {
                System.out.println("Course code already exists!");
                return;
            }
        }

        System.out.print("Enter course name: ");
        String name = scanner.nextLine();

        System.out.print("Enter instructor name: ");
        String instructor = scanner.nextLine();

        int credits = getIntInput("Enter credit hours: ");

        System.out.print("Enter schedule: ");
        String schedule = scanner.nextLine();

        int capacity = getIntInput("Enter class capacity: ");

        Course course = new Course(code, name, instructor, credits, schedule, capacity);
        courses.add(course);
        System.out.println("Course added successfully!");
    }

    private static void viewAllCourses() {
        System.out.println("\n=== All Courses ===");
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
            return;
        }

        for (int i = 0; i < courses.size(); i++) {
            System.out.println("\n--- Course " + (i + 1) + " ---");
            courses.get(i).displayInfo();
            System.out.println("Enrolled: " + getEnrollmentCount(courses.get(i).getCode()) + "/" + courses.get(i).getCapacity());
        }
    }

    private static void searchCourse() {
        System.out.println("\n=== Search Course ===");
        System.out.print("Enter course code or name to search: ");
        String searchTerm = scanner.nextLine().toLowerCase();

        boolean found = false;
        for (Course course : courses) {
            if (course.getCode().toLowerCase().contains(searchTerm) ||
                course.getName().toLowerCase().contains(searchTerm)) {
                course.displayInfo();
                System.out.println("Enrolled: " + getEnrollmentCount(course.getCode()) + "/" + course.getCapacity());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No course found matching: " + searchTerm);
        }
    }

    private static void enrollStudent() {
        System.out.println("\n=== Enroll Student in Course ===");
        System.out.print("Enter student name: ");
        String studentName = scanner.nextLine();

        System.out.print("Enter course code: ");
        String courseCode = scanner.nextLine().toUpperCase();

        // Find course
        Course course = null;
        for (Course c : courses) {
            if (c.getCode().equals(courseCode)) {
                course = c;
                break;
            }
        }

        if (course == null) {
            System.out.println("Course not found!");
            return;
        }

        // Check if course is full
        int enrolled = getEnrollmentCount(courseCode);
        if (enrolled >= course.getCapacity()) {
            System.out.println("Course is full!");
            return;
        }

        // Check if student is already enrolled
        for (Enrollment e : enrollments) {
            if (e.getStudentName().equalsIgnoreCase(studentName) && e.getCourseCode().equals(courseCode)) {
                System.out.println("Student is already enrolled in this course!");
                return;
            }
        }

        Enrollment enrollment = new Enrollment(studentName, courseCode, "2024");
        enrollments.add(enrollment);
        System.out.println("Student enrolled successfully!");
    }

    private static void viewEnrollments() {
        System.out.println("\n=== All Enrollments ===");
        if (enrollments.isEmpty()) {
            System.out.println("No enrollments found.");
            return;
        }

        for (Enrollment e : enrollments) {
            System.out.println("Student: " + e.getStudentName() + " | Course: " + e.getCourseCode() + " | Grade: " + e.getGrade() + " | Year: " + e.getAcademicYear());
        }
    }

    private static void assignGrade() {
        System.out.println("\n=== Assign Grade ===");
        System.out.print("Enter student name: ");
        String studentName = scanner.nextLine();

        System.out.print("Enter course code: ");
        String courseCode = scanner.nextLine().toUpperCase();

        // Find enrollment
        Enrollment enrollment = null;
        for (Enrollment e : enrollments) {
            if (e.getStudentName().equalsIgnoreCase(studentName) && e.getCourseCode().equals(courseCode)) {
                enrollment = e;
                break;
            }
        }

        if (enrollment == null) {
            System.out.println("Enrollment not found!");
            return;
        }

        System.out.print("Enter grade (A, B, C, D, F): ");
        String grade = scanner.nextLine().toUpperCase();

        if (!grade.matches("[A-F]")) {
            System.out.println("Invalid grade! Must be A, B, C, D, or F.");
            return;
        }

        enrollment.setGrade(grade);
        System.out.println("Grade assigned successfully!");
    }

    private static void generateTranscript() {
        System.out.println("\n=== Generate Student Transcript ===");
        System.out.print("Enter student name: ");
        String studentName = scanner.nextLine();

        ArrayList<Enrollment> studentEnrollments = new ArrayList<>();
        for (Enrollment e : enrollments) {
            if (e.getStudentName().equalsIgnoreCase(studentName)) {
                studentEnrollments.add(e);
            }
        }

        if (studentEnrollments.isEmpty()) {
            System.out.println("No enrollments found for student: " + studentName);
            return;
        }

        System.out.println("\n=== TRANSCRIPT FOR " + studentName.toUpperCase() + " ===");
        System.out.println("Academic Year: 2024");
        System.out.println("--------------------------------------------------");

        double totalGradePoints = 0;
        int totalCredits = 0;

        for (Enrollment e : studentEnrollments) {
            Course course = findCourseByCode(e.getCourseCode());
            if (course != null) {
                System.out.printf("%-10s %-30s %-5s %-5s%n",
                    course.getCode(), course.getName(), e.getGrade(), course.getCredits());

                double gradePoint = getGradePoint(e.getGrade());
                totalGradePoints += gradePoint * course.getCredits();
                totalCredits += course.getCredits();
            }
        }

        System.out.println("--------------------------------------------------");
        if (totalCredits > 0) {
            double gpa = totalGradePoints / totalCredits;
            System.out.printf("Total Credits: %d%n", totalCredits);
            System.out.printf("GPA: %.2f%n", gpa);
        }
    }

    private static void courseStatistics() {
        System.out.println("\n=== Course Statistics ===");
        System.out.println("Total Courses: " + courses.size());
        System.out.println("Total Enrollments: " + enrollments.size());

        // Most popular course
        String mostPopular = null;
        int maxEnrollments = 0;
        for (Course course : courses) {
            int count = getEnrollmentCount(course.getCode());
            if (count > maxEnrollments) {
                maxEnrollments = count;
                mostPopular = course.getName();
            }
        }

        if (mostPopular != null) {
            System.out.println("Most Popular Course: " + mostPopular + " (" + maxEnrollments + " students)");
        }

        // Average class size
        if (!courses.isEmpty()) {
            double totalEnrollment = 0;
            for (Course course : courses) {
                totalEnrollment += getEnrollmentCount(course.getCode());
            }
            double avgClassSize = totalEnrollment / courses.size();
            System.out.printf("Average Class Size: %.1f students%n", avgClassSize);
        }
    }

    private static int getEnrollmentCount(String courseCode) {
        int count = 0;
        for (Enrollment e : enrollments) {
            if (e.getCourseCode().equals(courseCode)) {
                count++;
            }
        }
        return count;
    }

    private static Course findCourseByCode(String code) {
        for (Course course : courses) {
            if (course.getCode().equals(code)) {
                return course;
            }
        }
        return null;
    }

    private static double getGradePoint(String grade) {
        switch (grade) {
            case "A": return 4.0;
            case "B": return 3.0;
            case "C": return 2.0;
            case "D": return 1.0;
            case "F": return 0.0;
            default: return 0.0;
        }
    }

    private static void loadCourseData() {
        File file = new File(COURSE_DATA_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 6) {
                    String code = data[0];
                    String name = data[1];
                    String instructor = data[2];
                    int credits = Integer.parseInt(data[3]);
                    String schedule = data[4];
                    int capacity = Integer.parseInt(data[5]);
                    courses.add(new Course(code, name, instructor, credits, schedule, capacity));
                }
            }
            System.out.println("Course data loaded.");
        } catch (IOException e) {
            System.out.println("Error loading course data: " + e.getMessage());
        }
    }

    private static void loadEnrollmentData() {
        File file = new File(ENROLLMENT_DATA_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 3) {
                    String studentName = data[0];
                    String courseCode = data[1];
                    String grade = data[2];
                    String academicYear = data.length > 3 ? data[3] : "2024";
                    Enrollment enrollment = new Enrollment(studentName, courseCode, academicYear);
                    enrollment.setGrade(grade);
                    enrollments.add(enrollment);
                }
            }
            System.out.println("Enrollment data loaded.");
        } catch (IOException e) {
            System.out.println("Error loading enrollment data: " + e.getMessage());
        }
    }

    private static void saveData() {
        // Save courses
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(COURSE_DATA_FILE))) {
            writer.println("Code,Name,Instructor,Credits,Schedule,Capacity");
            for (Course course : courses) {
                writer.printf("%s,%s,%s,%d,%s,%d%n",
                    course.getCode(), course.getName(), course.getInstructor(),
                    course.getCredits(), course.getSchedule(), course.getCapacity());
            }
        } catch (IOException e) {
            System.out.println("Error saving course data: " + e.getMessage());
        }

        // Save enrollments
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(ENROLLMENT_DATA_FILE))) {
            writer.println("StudentName,CourseCode,Grade,AcademicYear");
            for (Enrollment enrollment : enrollments) {
                writer.printf("%s,%s,%s,%s%n",
                    enrollment.getStudentName(), enrollment.getCourseCode(),
                    enrollment.getGrade(), enrollment.getAcademicYear());
            }
        } catch (IOException e) {
            System.out.println("Error saving enrollment data: " + e.getMessage());
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
}

class Course {
    private String code;
    private String name;
    private String instructor;
    private int credits;
    private String schedule;
    private int capacity;

    public Course(String code, String name, String instructor, int credits, String schedule, int capacity) {
        this.code = code;
        this.name = name;
        this.instructor = instructor;
        this.credits = credits;
        this.schedule = schedule;
        this.capacity = capacity;
    }

    public void displayInfo() {
        System.out.println("Course Code: " + code);
        System.out.println("Course Name: " + name);
        System.out.println("Instructor: " + instructor);
        System.out.println("Credits: " + credits);
        System.out.println("Schedule: " + schedule);
        System.out.println("Capacity: " + capacity);
    }

    // Getters
    public String getCode() { return code; }
    public String getName() { return name; }
    public String getInstructor() { return instructor; }
    public int getCredits() { return credits; }
    public String getSchedule() { return schedule; }
    public int getCapacity() { return capacity; }
}

class Enrollment {
    private String studentName;
    private String courseCode;
    private String grade;
    private String academicYear;

    public Enrollment(String studentName, String courseCode, String academicYear) {
        this.studentName = studentName;
        this.courseCode = courseCode;
        this.academicYear = academicYear;
        this.grade = "N/A"; // Not assigned yet
    }

    // Getters and setters
    public String getStudentName() { return studentName; }
    public String getCourseCode() { return courseCode; }
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
    public String getAcademicYear() { return academicYear; }
}
