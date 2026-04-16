import java.util.ArrayList;
import java.util.Scanner;

public class OOPTesting {
    public static void main(String[] args) {


        ArrayList<Student> students = new ArrayList<>();
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add Student");
            System.out.println("2. Show Students");
            System.out.println("3. Top Student");
            System.out.println("4. Lowest Student");
            System.out.println("5. Search Student");
            System.out.println("6. Exit");

            int choice = input.nextInt();
            input.nextLine();

            if (choice == 1) {
                System.out.print("Enter name: ");
                String name = input.nextLine();

                System.out.print("Enter score: ");
                int score = input.nextInt();
                input.nextLine();


                Student s = new Student();
                s.name = name;
                s.score = score;

                students.add(s);

            } else if (choice == 2) {
                for (Student s : students) {
                    System.out.println(s.name + " - " + s.score);
                }

            } else if (choice == 3) {
                if (!students.isEmpty()) {
                    Student top = students.get(0); //For now I will assume the FIRST student is the best

                    for (Student s : students) {
                        if (s.score > top.score) {
                            top = s; // for updating the top student
                        }
                    }

                    System.out.println("Top student: " + top.name + " --- The score :  " + top.score);
                }
            } else {
                if (choice == 4) {
                    if (!students.isEmpty()) {
                        Student lowest = students.get(0);
                        for (Student s : students) {
                            if (s.score < lowest.score) {
                                lowest = s;
                            }
                        }
                        System.out.println("Lowest student: " + lowest.name + " - " + lowest.score);
                    }

                } else if (choice == 5) {

                    System.out.print("Enter name to search: ");
                    String searchName = input.nextLine();

                    boolean found = false;

                    for (Student s : students) {
                        if (s.name.equals(searchName)) {
                            System.out.println("Found: " + s.name + " - " + s.score);
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        System.out.println("Student not found");
                    }
                }
                if (choice == 6) {
                    System.out.println("Exiting...");
                    break;
                }


            }
        }

    }
}



class Student {
    String name;
    int score;
}








