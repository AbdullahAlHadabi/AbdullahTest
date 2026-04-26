import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class LibraryManagementSystem {
    private static ArrayList<Book> books = new ArrayList<>();
    private static ArrayList<Member> members = new ArrayList<>();
    private static ArrayList<BorrowRecord> borrowRecords = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static final String BOOKS_FILE = "books_data.txt";
    private static final String MEMBERS_FILE = "members_data.txt";
    private static final String BORROWS_FILE = "borrows_data.txt";
    private static final int MAX_BORROW_DAYS = 14;
    private static final double LATE_FEE_PER_DAY = 0.50;

    public static void main(String[] args) {
        System.out.println("=== Library Management System ===");
        System.out.println("Manage books, members, and borrowing records\n");

        // Load existing data
        loadData();

        // Add sample data if empty
        if (books.isEmpty()) {
            initializeSampleData();
        }

        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    viewAllBooks();
                    break;
                case 3:
                    searchBook();
                    break;
                case 4:
                    addMember();
                    break;
                case 5:
                    viewAllMembers();
                    break;
                case 6:
                    borrowBook();
                    break;
                case 7:
                    returnBook();
                    break;
                case 8:
                    viewBorrowRecords();
                    break;
                case 9:
                    calculateFines();
                    break;
                case 10:
                    libraryStatistics();
                    break;
                case 11:
                    System.out.println("Thank you for using Library Management System!");
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
        System.out.println("\n=== Library Management Menu ===");
        System.out.println("1. Add New Book");
        System.out.println("2. View All Books");
        System.out.println("3. Search Book");
        System.out.println("4. Add New Member");
        System.out.println("5. View All Members");
        System.out.println("6. Borrow Book");
        System.out.println("7. Return Book");
        System.out.println("8. View Borrow Records");
        System.out.println("9. Calculate Fines");
        System.out.println("10. Library Statistics");
        System.out.println("11. Exit");
        System.out.println("===============================");
    }

    private static void initializeSampleData() {
        // Add sample books
        books.add(new Book("B001", "Java Programming", "John Smith", "Tech Books", 2020, 5));
        books.add(new Book("B002", "Data Structures", "Jane Doe", "Academic Press", 2019, 3));
        books.add(new Book("B003", "Database Systems", "Bob Johnson", "Tech Books", 2021, 4));
        books.add(new Book("B004", "Web Development", "Alice Brown", "Web Publishers", 2022, 6));

        // Add sample members
        members.add(new Member("M001", "Ahmed Abdullah", "ahmed@email.com", "123-456-7890"));
        members.add(new Member("M002", "Sara Mohamed", "sara@email.com", "123-456-7891"));
        members.add(new Member("M003", "Omar Hassan", "omar@email.com", "123-456-7892"));

        System.out.println("Sample data initialized.");
    }

    private static void addBook() {
        System.out.println("\n=== Add New Book ===");
        System.out.print("Enter book ID: ");
        String id = scanner.nextLine().toUpperCase();

        // Check if book ID already exists
        for (Book book : books) {
            if (book.getId().equals(id)) {
                System.out.println("Book ID already exists!");
                return;
            }
        }

        System.out.print("Enter book title: ");
        String title = scanner.nextLine();

        System.out.print("Enter author: ");
        String author = scanner.nextLine();

        System.out.print("Enter publisher: ");
        String publisher = scanner.nextLine();

        int year = getIntInput("Enter publication year: ");

        int copies = getIntInput("Enter number of copies: ");

        Book book = new Book(id, title, author, publisher, year, copies);
        books.add(book);
        System.out.println("Book added successfully!");
    }

    private static void viewAllBooks() {
        System.out.println("\n=== All Books ===");
        if (books.isEmpty()) {
            System.out.println("No books found.");
            return;
        }

        for (int i = 0; i < books.size(); i++) {
            System.out.println("\n--- Book " + (i + 1) + " ---");
            books.get(i).displayInfo();
            int available = books.get(i).getAvailableCopies();
            System.out.println("Available Copies: " + available);
        }
    }

    private static void searchBook() {
        System.out.println("\n=== Search Book ===");
        System.out.print("Enter book title, author, or ID to search: ");
        String searchTerm = scanner.nextLine().toLowerCase();

        boolean found = false;
        for (Book book : books) {
            if (book.getId().toLowerCase().contains(searchTerm) ||
                book.getTitle().toLowerCase().contains(searchTerm) ||
                book.getAuthor().toLowerCase().contains(searchTerm)) {
                book.displayInfo();
                System.out.println("Available Copies: " + book.getAvailableCopies());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No book found matching: " + searchTerm);
        }
    }

    private static void addMember() {
        System.out.println("\n=== Add New Member ===");
        System.out.print("Enter member ID: ");
        String id = scanner.nextLine().toUpperCase();

        // Check if member ID already exists
        for (Member member : members) {
            if (member.getId().equals(id)) {
                System.out.println("Member ID already exists!");
                return;
            }
        }

        System.out.print("Enter member name: ");
        String name = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();

        Member member = new Member(id, name, email, phone);
        members.add(member);
        System.out.println("Member added successfully!");
    }

    private static void viewAllMembers() {
        System.out.println("\n=== All Members ===");
        if (members.isEmpty()) {
            System.out.println("No members found.");
            return;
        }

        for (int i = 0; i < members.size(); i++) {
            System.out.println("\n--- Member " + (i + 1) + " ---");
            members.get(i).displayInfo();
            int borrowedBooks = getBorrowedBooksCount(members.get(i).getId());
            System.out.println("Currently Borrowed Books: " + borrowedBooks);
        }
    }

    private static void borrowBook() {
        System.out.println("\n=== Borrow Book ===");
        System.out.print("Enter member ID: ");
        String memberId = scanner.nextLine().toUpperCase();

        System.out.print("Enter book ID: ");
        String bookId = scanner.nextLine().toUpperCase();

        // Find member
        Member member = findMemberById(memberId);
        if (member == null) {
            System.out.println("Member not found!");
            return;
        }

        // Find book
        Book book = findBookById(bookId);
        if (book == null) {
            System.out.println("Book not found!");
            return;
        }

        // Check if book is available
        if (book.getAvailableCopies() <= 0) {
            System.out.println("Book is not available!");
            return;
        }

        // Check if member already has this book
        for (BorrowRecord record : borrowRecords) {
            if (record.getMemberId().equals(memberId) && record.getBookId().equals(bookId) && record.getReturnDate() == null) {
                System.out.println("Member already has this book!");
                return;
            }
        }

        // Check borrowing limit (max 3 books per member)
        int currentBorrows = getBorrowedBooksCount(memberId);
        if (currentBorrows >= 3) {
            System.out.println("Member has reached the borrowing limit (3 books)!");
            return;
        }

        // Create borrow record
        LocalDate borrowDate = LocalDate.now();
        BorrowRecord record = new BorrowRecord(memberId, bookId, borrowDate);
        borrowRecords.add(record);

        // Update book availability
        book.setAvailableCopies(book.getAvailableCopies() - 1);

        System.out.println("Book borrowed successfully!");
        System.out.println("Due date: " + borrowDate.plusDays(MAX_BORROW_DAYS));
    }

    private static void returnBook() {
        System.out.println("\n=== Return Book ===");
        System.out.print("Enter member ID: ");
        String memberId = scanner.nextLine().toUpperCase();

        System.out.print("Enter book ID: ");
        String bookId = scanner.nextLine().toUpperCase();

        // Find the borrow record
        BorrowRecord record = null;
        for (BorrowRecord r : borrowRecords) {
            if (r.getMemberId().equals(memberId) && r.getBookId().equals(bookId) && r.getReturnDate() == null) {
                record = r;
                break;
            }
        }

        if (record == null) {
            System.out.println("No active borrow record found for this member and book!");
            return;
        }

        // Set return date
        LocalDate returnDate = LocalDate.now();
        record.setReturnDate(returnDate);

        // Update book availability
        Book book = findBookById(bookId);
        if (book != null) {
            book.setAvailableCopies(book.getAvailableCopies() + 1);
        }

        // Calculate fine if overdue
        long daysOverdue = ChronoUnit.DAYS.between(record.getDueDate(), returnDate);
        if (daysOverdue > 0) {
            double fine = daysOverdue * LATE_FEE_PER_DAY;
            System.out.printf("Book returned late! Fine: $%.2f (%d days overdue)%n", fine, daysOverdue);
        } else {
            System.out.println("Book returned on time. Thank you!");
        }
    }

    private static void viewBorrowRecords() {
        System.out.println("\n=== Borrow Records ===");
        if (borrowRecords.isEmpty()) {
            System.out.println("No borrow records found.");
            return;
        }

        for (BorrowRecord record : borrowRecords) {
            System.out.println("\n--- Record ---");
            System.out.println("Member ID: " + record.getMemberId());
            System.out.println("Book ID: " + record.getBookId());
            System.out.println("Borrow Date: " + record.getBorrowDate());
            System.out.println("Due Date: " + record.getDueDate());
            if (record.getReturnDate() != null) {
                System.out.println("Return Date: " + record.getReturnDate());
                long daysOverdue = ChronoUnit.DAYS.between(record.getDueDate(), record.getReturnDate());
                if (daysOverdue > 0) {
                    System.out.println("Days Overdue: " + daysOverdue);
                }
            } else {
                System.out.println("Status: Not Returned");
            }
        }
    }

    private static void calculateFines() {
        System.out.println("\n=== Calculate Fines ===");
        System.out.print("Enter member ID: ");
        String memberId = scanner.nextLine().toUpperCase();

        Member member = findMemberById(memberId);
        if (member == null) {
            System.out.println("Member not found!");
            return;
        }

        double totalFine = 0;
        LocalDate today = LocalDate.now();

        System.out.println("\nFines for member: " + member.getName());
        System.out.println("--------------------------------");

        for (BorrowRecord record : borrowRecords) {
            if (record.getMemberId().equals(memberId) && record.getReturnDate() != null) {
                long daysOverdue = ChronoUnit.DAYS.between(record.getDueDate(), record.getReturnDate());
                if (daysOverdue > 0) {
                    double fine = daysOverdue * LATE_FEE_PER_DAY;
                    totalFine += fine;
                    System.out.printf("Book %s: $%.2f (%d days overdue)%n", record.getBookId(), fine, daysOverdue);
                }
            } else if (record.getMemberId().equals(memberId) && record.getReturnDate() == null) {
                // Check for overdue unreturned books
                long daysOverdue = ChronoUnit.DAYS.between(record.getDueDate(), today);
                if (daysOverdue > 0) {
                    double fine = daysOverdue * LATE_FEE_PER_DAY;
                    totalFine += fine;
                    System.out.printf("Book %s: $%.2f (%d days overdue - NOT RETURNED)%n", record.getBookId(), fine, daysOverdue);
                }
            }
        }

        System.out.println("--------------------------------");
        System.out.printf("Total Fine: $%.2f%n", totalFine);
    }

    private static void libraryStatistics() {
        System.out.println("\n=== Library Statistics ===");
        System.out.println("Total Books: " + books.size());
        System.out.println("Total Members: " + members.size());
        System.out.println("Total Borrow Records: " + borrowRecords.size());

        // Calculate total book copies
        int totalCopies = 0;
        for (Book book : books) {
            totalCopies += book.getTotalCopies();
        }
        System.out.println("Total Book Copies: " + totalCopies);

        // Calculate currently borrowed books
        int currentlyBorrowed = 0;
        for (BorrowRecord record : borrowRecords) {
            if (record.getReturnDate() == null) {
                currentlyBorrowed++;
            }
        }
        System.out.println("Currently Borrowed: " + currentlyBorrowed);

        // Most popular book
        String popularBook = null;
        int maxBorrows = 0;
        for (Book book : books) {
            int borrowCount = 0;
            for (BorrowRecord record : borrowRecords) {
                if (record.getBookId().equals(book.getId())) {
                    borrowCount++;
                }
            }
            if (borrowCount > maxBorrows) {
                maxBorrows = borrowCount;
                popularBook = book.getTitle();
            }
        }

        if (popularBook != null) {
            System.out.println("Most Popular Book: " + popularBook + " (" + maxBorrows + " borrows)");
        }

        // Calculate overdue books
        LocalDate today = LocalDate.now();
        int overdueBooks = 0;
        for (BorrowRecord record : borrowRecords) {
            if (record.getReturnDate() == null && record.getDueDate().isBefore(today)) {
                overdueBooks++;
            }
        }
        System.out.println("Overdue Books: " + overdueBooks);
    }

    private static Member findMemberById(String id) {
        for (Member member : members) {
            if (member.getId().equals(id)) {
                return member;
            }
        }
        return null;
    }

    private static Book findBookById(String id) {
        for (Book book : books) {
            if (book.getId().equals(id)) {
                return book;
            }
        }
        return null;
    }

    private static int getBorrowedBooksCount(String memberId) {
        int count = 0;
        for (BorrowRecord record : borrowRecords) {
            if (record.getMemberId().equals(memberId) && record.getReturnDate() == null) {
                count++;
            }
        }
        return count;
    }

    private static void loadData() {
        // Load books
        File booksFile = new File(BOOKS_FILE);
        if (booksFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(booksFile))) {
                String line;
                reader.readLine(); // Skip header
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data.length >= 6) {
                        String id = data[0];
                        String title = data[1];
                        String author = data[2];
                        String publisher = data[3];
                        int year = Integer.parseInt(data[4]);
                        int copies = Integer.parseInt(data[5]);
                        books.add(new Book(id, title, author, publisher, year, copies));
                    }
                }
            } catch (IOException e) {
                System.out.println("Error loading books: " + e.getMessage());
            }
        }

        // Load members
        File membersFile = new File(MEMBERS_FILE);
        if (membersFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(membersFile))) {
                String line;
                reader.readLine(); // Skip header
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data.length >= 4) {
                        String id = data[0];
                        String name = data[1];
                        String email = data[2];
                        String phone = data[3];
                        members.add(new Member(id, name, email, phone));
                    }
                }
            } catch (IOException e) {
                System.out.println("Error loading members: " + e.getMessage());
            }
        }

        // Load borrow records
        File borrowsFile = new File(BORROWS_FILE);
        if (borrowsFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(borrowsFile))) {
                String line;
                reader.readLine(); // Skip header
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data.length >= 4) {
                        String memberId = data[0];
                        String bookId = data[1];
                        LocalDate borrowDate = LocalDate.parse(data[2]);
                        LocalDate dueDate = LocalDate.parse(data[3]);
                        BorrowRecord record = new BorrowRecord(memberId, bookId, borrowDate);
                        if (data.length > 4 && !data[4].equals("null")) {
                            record.setReturnDate(LocalDate.parse(data[4]));
                        }
                        borrowRecords.add(record);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error loading borrow records: " + e.getMessage());
            }
        }
    }

    private static void saveData() {
        // Save books
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(BOOKS_FILE))) {
            writer.println("ID,Title,Author,Publisher,Year,Copies");
            for (Book book : books) {
                writer.printf("%s,%s,%s,%s,%d,%d%n",
                    book.getId(), book.getTitle(), book.getAuthor(),
                    book.getPublisher(), book.getYear(), book.getTotalCopies());
            }
        } catch (IOException e) {
            System.out.println("Error saving books: " + e.getMessage());
        }

        // Save members
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(MEMBERS_FILE))) {
            writer.println("ID,Name,Email,Phone");
            for (Member member : members) {
                writer.printf("%s,%s,%s,%s%n",
                    member.getId(), member.getName(), member.getEmail(), member.getPhone());
            }
        } catch (IOException e) {
            System.out.println("Error saving members: " + e.getMessage());
        }

        // Save borrow records
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(BORROWS_FILE))) {
            writer.println("MemberID,BookID,BorrowDate,DueDate,ReturnDate");
            for (BorrowRecord record : borrowRecords) {
                writer.printf("%s,%s,%s,%s,%s%n",
                    record.getMemberId(), record.getBookId(),
                    record.getBorrowDate(), record.getDueDate(),
                    record.getReturnDate() != null ? record.getReturnDate() : "null");
            }
        } catch (IOException e) {
            System.out.println("Error saving borrow records: " + e.getMessage());
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

class Book {
    private String id;
    private String title;
    private String author;
    private String publisher;
    private int year;
    private int totalCopies;
    private int availableCopies;

    public Book(String id, String title, String author, String publisher, int year, int totalCopies) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies; // Initially all copies are available
    }

    public void displayInfo() {
        System.out.println("Book ID: " + id);
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Publisher: " + publisher);
        System.out.println("Year: " + year);
        System.out.println("Total Copies: " + totalCopies);
    }

    // Getters and setters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getPublisher() { return publisher; }
    public int getYear() { return year; }
    public int getTotalCopies() { return totalCopies; }
    public int getAvailableCopies() { return availableCopies; }
    public void setAvailableCopies(int availableCopies) { this.availableCopies = availableCopies; }
}

class Member {
    private String id;
    private String name;
    private String email;
    private String phone;

    public Member(String id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public void displayInfo() {
        System.out.println("Member ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
}

class BorrowRecord {
    private String memberId;
    private String bookId;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    public BorrowRecord(String memberId, String bookId, LocalDate borrowDate) {
        this.memberId = memberId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.dueDate = borrowDate.plusDays(14); // 14 days borrowing period
        this.returnDate = null;
    }

    // Getters and setters
    public String getMemberId() { return memberId; }
    public String getBookId() { return bookId; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
}
