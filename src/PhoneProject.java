import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class PhoneProject {
    public static void main(String[] args) {
        HashMap<String, String> contacts = new HashMap<>();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("----- Phone Directory -----");
            System.out.println("1. Add Contact");
            System.out.println("2. Search Contact");
            System.out.println("3. Delete Contact");
            System.out.println("4. Show All Contacts");
            System.out.println("5. Exit");

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                System.out.println("Enter Contact Name:");
                String name = sc.nextLine();
                System.out.println("Enter Contact Number:");
                String number = sc.nextLine();

                contacts.put(name, number);
                System.out.println("Contact added successfully!");
            }

            // here we search for contact
            else if (choice == 2) {
                System.out.println("Enter Contact Name:");
                String name = sc.nextLine();

                if(contacts.containsKey(name)) {
                    System.out.println("Number : " + contacts.get(name));
                }else  {
                    System.out.println("Contact does not found");
                }
            }
            //delete contact
            else if (choice == 3) {
                System.out.println("Enter Contact Name to Delete: ");
                String name = sc.nextLine();

                if(contacts.containsKey(name)) {
                    contacts.remove(name);
                    System.out.println("Contact deleted successfully :) ");
                }else {}
                    System.out.println("Contact does not found");
                }
            else if (choice == 4) {
                System.out.println("All Contacts:");
                for (String name : contacts.keySet()) {
                    System.out.println("Name: " + name + ", Number: " + contacts.get(name));
                }
            } else if (choice == 5) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
       }

    }
}



