import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class PhoneProject {
    public static void main(String[] args) {

        ArrayList<Contact> contactsList = new ArrayList<>();
        HashMap<String, Contact> contacts = new HashMap<>();
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


                Contact c = new Contact(name, number);

                contacts.put(name, c);
                contactsList.add(c);
                System.out.println("Contact added successfully :) ");
            }

            // here we search for contact
            else if (choice == 2) {
                System.out.println("Enter Contact Name:");
                String name = sc.nextLine();

                if(contacts.containsKey(name)) {
                    Contact c = contacts.get(name);
                    System.out.println("Found: " + c.name + " - " + c.number);
                }else  {
                    System.out.println("Contact does not found");
                }
            }
            //delete contact
            else if (choice == 3) {
                System.out.println("Enter Contact Name to Delete: ");
                String name = sc.nextLine().toLowerCase();

                if (contacts.containsKey(name)) {
                    contacts.remove(name);
                    System.out.println("Contact deleted successfully :) ");
                } else {
                    System.out.println("Contact does not found");
                }

                // Show All SORTED
            }else if (choice == 4) {

                    if (contactsList.isEmpty()) {
                        System.out.println("No contacts.");
                        continue;
                    }

                    contactsList.sort((c1, c2) -> c1.name.compareTo(c2.name));

                    for (Contact c : contactsList) {
                        System.out.println(c.name + " - " + c.number);
                    }
                }

                // 🔹 Exit
                else if (choice == 5) {
                    System.out.println("Goodbye see you soon :) ");
                    break;
                }

                else {
                    System.out.println("Invalid choice.");
                }
            }
        }
    }
 class Contact {
    String name;
    String number;
    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
    }
}


