import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BankAccountManagementSystem {
    private static ArrayList<BankAccount> accounts = new ArrayList<>();
    private static ArrayList<Transaction> transactions = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static final String ACCOUNTS_FILE = "bank_accounts.txt";
    private static final String TRANSACTIONS_FILE = "bank_transactions.txt";
    private static int nextAccountNumber = 1001;

    public static void main(String[] args) {
        System.out.println("=== Bank Account Management System ===");
        System.out.println("Manage accounts, transactions, and financial records\n");

        // Load existing data
        loadData();

        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    viewAllAccounts();
                    break;
                case 3:
                    searchAccount();
                    break;
                case 4:
                    depositMoney();
                    break;
                case 5:
                    withdrawMoney();
                    break;
                case 6:
                    transferMoney();
                    break;
                case 7:
                    viewTransactionHistory();
                    break;
                case 8:
                    calculateInterest();
                    break;
                case 9:
                    bankStatistics();
                    break;
                case 10:
                    System.out.println("Thank you for using Bank Account Management System!");
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
        System.out.println("\n=== Bank Account Management Menu ===");
        System.out.println("1. Create New Account");
        System.out.println("2. View All Accounts");
        System.out.println("3. Search Account");
        System.out.println("4. Deposit Money");
        System.out.println("5. Withdraw Money");
        System.out.println("6. Transfer Money");
        System.out.println("7. View Transaction History");
        System.out.println("8. Calculate Interest");
        System.out.println("9. Bank Statistics");
        System.out.println("10. Exit");
        System.out.println("====================================");
    }

    private static void createAccount() {
        System.out.println("\n=== Create New Account ===");
        System.out.println("1. Savings Account");
        System.out.println("2. Checking Account");

        int type = getIntInput("Enter account type: ");

        System.out.print("Enter account holder name: ");
        String name = scanner.nextLine();

        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        double initialDeposit = getDoubleInput("Enter initial deposit amount: ");

        if (initialDeposit < 0) {
            System.out.println("Initial deposit cannot be negative!");
            return;
        }

        int accountNumber = nextAccountNumber++;
        BankAccount account;

        if (type == 1) {
            double interestRate = getDoubleInput("Enter interest rate (%): ");
            account = new SavingsAccount(accountNumber, name, address, phone, email, initialDeposit, interestRate);
        } else if (type == 2) {
            double overdraftLimit = getDoubleInput("Enter overdraft limit: ");
            account = new CheckingAccount(accountNumber, name, address, phone, email, initialDeposit, overdraftLimit);
        } else {
            System.out.println("Invalid account type!");
            return;
        }

        accounts.add(account);

        // Record initial deposit transaction
        Transaction transaction = new Transaction(accountNumber, "Deposit", initialDeposit, "Initial deposit", LocalDateTime.now());
        transactions.add(transaction);

        System.out.println("Account created successfully!");
        System.out.println("Account Number: " + accountNumber);
    }

    private static void viewAllAccounts() {
        System.out.println("\n=== All Accounts ===");
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }

        for (int i = 0; i < accounts.size(); i++) {
            System.out.println("\n--- Account " + (i + 1) + " ---");
            accounts.get(i).displayInfo();
        }
    }

    private static void searchAccount() {
        System.out.println("\n=== Search Account ===");
        System.out.print("Enter account number: ");
        int accountNumber = getIntInput("");

        BankAccount account = findAccountByNumber(accountNumber);
        if (account == null) {
            System.out.println("Account not found!");
            return;
        }

        account.displayInfo();
    }

    private static void depositMoney() {
        System.out.println("\n=== Deposit Money ===");
        System.out.print("Enter account number: ");
        int accountNumber = getIntInput("");

        BankAccount account = findAccountByNumber(accountNumber);
        if (account == null) {
            System.out.println("Account not found!");
            return;
        }

        double amount = getDoubleInput("Enter deposit amount: ");
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive!");
            return;
        }

        account.deposit(amount);

        // Record transaction
        Transaction transaction = new Transaction(accountNumber, "Deposit", amount, "Cash deposit", LocalDateTime.now());
        transactions.add(transaction);

        System.out.println("Deposit successful!");
        System.out.println("New balance: $" + String.format("%.2f", account.getBalance()));
    }

    private static void withdrawMoney() {
        System.out.println("\n=== Withdraw Money ===");
        System.out.print("Enter account number: ");
        int accountNumber = getIntInput("");

        BankAccount account = findAccountByNumber(accountNumber);
        if (account == null) {
            System.out.println("Account not found!");
            return;
        }

        double amount = getDoubleInput("Enter withdrawal amount: ");
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive!");
            return;
        }

        if (!account.withdraw(amount)) {
            System.out.println("Insufficient funds!");
            return;
        }

        // Record transaction
        Transaction transaction = new Transaction(accountNumber, "Withdrawal", amount, "ATM withdrawal", LocalDateTime.now());
        transactions.add(transaction);

        System.out.println("Withdrawal successful!");
        System.out.println("New balance: $" + String.format("%.2f", account.getBalance()));
    }

    private static void transferMoney() {
        System.out.println("\n=== Transfer Money ===");
        System.out.print("Enter sender account number: ");
        int fromAccountNumber = getIntInput("");

        BankAccount fromAccount = findAccountByNumber(fromAccountNumber);
        if (fromAccount == null) {
            System.out.println("Sender account not found!");
            return;
        }

        System.out.print("Enter receiver account number: ");
        int toAccountNumber = getIntInput("");

        BankAccount toAccount = findAccountByNumber(toAccountNumber);
        if (toAccount == null) {
            System.out.println("Receiver account not found!");
            return;
        }

        if (fromAccountNumber == toAccountNumber) {
            System.out.println("Cannot transfer to the same account!");
            return;
        }

        double amount = getDoubleInput("Enter transfer amount: ");
        if (amount <= 0) {
            System.out.println("Transfer amount must be positive!");
            return;
        }

        if (!fromAccount.withdraw(amount)) {
            System.out.println("Insufficient funds in sender account!");
            return;
        }

        toAccount.deposit(amount);

        // Record transactions
        Transaction debitTransaction = new Transaction(fromAccountNumber, "Transfer Out", amount,
            "Transfer to account " + toAccountNumber, LocalDateTime.now());
        Transaction creditTransaction = new Transaction(toAccountNumber, "Transfer In", amount,
            "Transfer from account " + fromAccountNumber, LocalDateTime.now());

        transactions.add(debitTransaction);
        transactions.add(creditTransaction);

        System.out.println("Transfer successful!");
        System.out.println("Sender new balance: $" + String.format("%.2f", fromAccount.getBalance()));
        System.out.println("Receiver new balance: $" + String.format("%.2f", toAccount.getBalance()));
    }

    private static void viewTransactionHistory() {
        System.out.println("\n=== Transaction History ===");
        System.out.print("Enter account number: ");
        int accountNumber = getIntInput("");

        ArrayList<Transaction> accountTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getAccountNumber() == accountNumber) {
                accountTransactions.add(transaction);
            }
        }

        if (accountTransactions.isEmpty()) {
            System.out.println("No transactions found for this account.");
            return;
        }

        System.out.println("\nTransaction History for Account: " + accountNumber);
        System.out.println("------------------------------------------------------------");
        System.out.printf("%-12s %-15s %-10s %-20s %-15s%n", "Date", "Type", "Amount", "Description", "Balance");

        double runningBalance = 0;
        // Calculate initial balance from transactions
        for (Transaction t : accountTransactions) {
            if (t.getType().equals("Deposit") || t.getType().equals("Transfer In")) {
                runningBalance += t.getAmount();
            } else if (t.getType().equals("Withdrawal") || t.getType().equals("Transfer Out")) {
                runningBalance -= t.getAmount();
            }
        }

        // Sort transactions by date (assuming they are added in chronological order)
        for (int i = accountTransactions.size() - 1; i >= 0; i--) {
            Transaction t = accountTransactions.get(i);
            String balanceStr = "$" + String.format("%.2f", runningBalance);

            if (t.getType().equals("Deposit") || t.getType().equals("Transfer In")) {
                runningBalance -= t.getAmount();
            } else if (t.getType().equals("Withdrawal") || t.getType().equals("Transfer Out")) {
                runningBalance += t.getAmount();
            }

            System.out.printf("%-12s %-15s $%-9.2f %-20s %-15s%n",
                t.getDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")),
                t.getType(),
                t.getAmount(),
                t.getDescription(),
                balanceStr);
        }
    }

    private static void calculateInterest() {
        System.out.println("\n=== Calculate Interest ===");
        System.out.print("Enter account number: ");
        int accountNumber = getIntInput("");

        BankAccount account = findAccountByNumber(accountNumber);
        if (account == null) {
            System.out.println("Account not found!");
            return;
        }

        if (!(account instanceof SavingsAccount)) {
            System.out.println("Interest calculation is only available for savings accounts!");
            return;
        }

        SavingsAccount savingsAccount = (SavingsAccount) account;
        double interest = savingsAccount.calculateInterest();

        System.out.println("Interest calculated: $" + String.format("%.2f", interest));
        System.out.println("Current balance: $" + String.format("%.2f", account.getBalance()));
        System.out.println("Balance after interest: $" + String.format("%.2f", account.getBalance() + interest));

        System.out.print("Do you want to add interest to account? (y/n): ");
        String choice = scanner.nextLine().toLowerCase();

        if (choice.equals("y") || choice.equals("yes")) {
            account.deposit(interest);
            Transaction transaction = new Transaction(accountNumber, "Interest", interest, "Monthly interest", LocalDateTime.now());
            transactions.add(transaction);
            System.out.println("Interest added to account!");
        }
    }

    private static void bankStatistics() {
        System.out.println("\n=== Bank Statistics ===");
        System.out.println("Total Accounts: " + accounts.size());
        System.out.println("Total Transactions: " + transactions.size());

        double totalBalance = 0;
        int savingsCount = 0;
        int checkingCount = 0;

        for (BankAccount account : accounts) {
            totalBalance += account.getBalance();
            if (account instanceof SavingsAccount) {
                savingsCount++;
            } else if (account instanceof CheckingAccount) {
                checkingCount++;
            }
        }

        System.out.println("Savings Accounts: " + savingsCount);
        System.out.println("Checking Accounts: " + checkingCount);
        System.out.println("Total Bank Balance: $" + String.format("%.2f", totalBalance));

        if (!accounts.isEmpty()) {
            double avgBalance = totalBalance / accounts.size();
            System.out.println("Average Account Balance: $" + String.format("%.2f", avgBalance));
        }

        // Most active account (most transactions)
        int mostActiveAccount = -1;
        int maxTransactions = 0;
        for (BankAccount account : accounts) {
            int transactionCount = 0;
            for (Transaction transaction : transactions) {
                if (transaction.getAccountNumber() == account.getAccountNumber()) {
                    transactionCount++;
                }
            }
            if (transactionCount > maxTransactions) {
                maxTransactions = transactionCount;
                mostActiveAccount = account.getAccountNumber();
            }
        }

        if (mostActiveAccount != -1) {
            System.out.println("Most Active Account: " + mostActiveAccount + " (" + maxTransactions + " transactions)");
        }
    }

    private static BankAccount findAccountByNumber(int accountNumber) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }
        return null;
    }

    private static void loadData() {
        // Load accounts
        File accountsFile = new File(ACCOUNTS_FILE);
        if (accountsFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(accountsFile))) {
                String line;
                reader.readLine(); // Skip header
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data.length >= 8) {
                        int accountNumber = Integer.parseInt(data[0]);
                        String name = data[1];
                        String address = data[2];
                        String phone = data[3];
                        String email = data[4];
                        double balance = Double.parseDouble(data[5]);
                        String type = data[6];

                        BankAccount account;
                        if (type.equals("Savings")) {
                            double interestRate = Double.parseDouble(data[7]);
                            account = new SavingsAccount(accountNumber, name, address, phone, email, balance, interestRate);
                        } else {
                            double overdraftLimit = Double.parseDouble(data[7]);
                            account = new CheckingAccount(accountNumber, name, address, phone, email, balance, overdraftLimit);
                        }
                        accounts.add(account);

                        // Update next account number
                        if (accountNumber >= nextAccountNumber) {
                            nextAccountNumber = accountNumber + 1;
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Error loading accounts: " + e.getMessage());
            }
        }

        // Load transactions
        File transactionsFile = new File(TRANSACTIONS_FILE);
        if (transactionsFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(transactionsFile))) {
                String line;
                reader.readLine(); // Skip header
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data.length >= 5) {
                        int accountNumber = Integer.parseInt(data[0]);
                        String type = data[1];
                        double amount = Double.parseDouble(data[2]);
                        String description = data[3];
                        LocalDateTime dateTime = LocalDateTime.parse(data[4], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                        Transaction transaction = new Transaction(accountNumber, type, amount, description, dateTime);
                        transactions.add(transaction);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error loading transactions: " + e.getMessage());
            }
        }
    }

    private static void saveData() {
        // Save accounts
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(ACCOUNTS_FILE))) {
            writer.println("AccountNumber,Name,Address,Phone,Email,Balance,Type,Rate/Limit");
            for (BankAccount account : accounts) {
                writer.print(account.getAccountNumber() + ",");
                writer.print(account.getName() + ",");
                writer.print(account.getAddress() + ",");
                writer.print(account.getPhone() + ",");
                writer.print(account.getEmail() + ",");
                writer.print(account.getBalance() + ",");
                if (account instanceof SavingsAccount) {
                    writer.print("Savings," + ((SavingsAccount) account).getInterestRate());
                } else if (account instanceof CheckingAccount) {
                    writer.print("Checking," + ((CheckingAccount) account).getOverdraftLimit());
                }
                writer.println();
            }
        } catch (IOException e) {
            System.out.println("Error saving accounts: " + e.getMessage());
        }

        // Save transactions
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(TRANSACTIONS_FILE))) {
            writer.println("AccountNumber,Type,Amount,Description,DateTime");
            for (Transaction transaction : transactions) {
                writer.printf("%d,%s,%.2f,%s,%s%n",
                    transaction.getAccountNumber(),
                    transaction.getType(),
                    transaction.getAmount(),
                    transaction.getDescription(),
                    transaction.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
        } catch (IOException e) {
            System.out.println("Error saving transactions: " + e.getMessage());
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

// Abstract base class for bank accounts
abstract class BankAccount {
    private int accountNumber;
    private String name;
    private String address;
    private String phone;
    private String email;
    protected double balance;

    public BankAccount(int accountNumber, String name, String address, String phone, String email, double balance) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.balance = balance;
    }

    public abstract void displayInfo();
    public abstract boolean withdraw(double amount);

    public void deposit(double amount) {
        balance += amount;
    }

    // Getters
    public int getAccountNumber() { return accountNumber; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public double getBalance() { return balance; }
}

// Savings account class
class SavingsAccount extends BankAccount {
    private double interestRate;

    public SavingsAccount(int accountNumber, String name, String address, String phone, String email, double balance, double interestRate) {
        super(accountNumber, name, address, phone, email, balance);
        this.interestRate = interestRate;
    }

    @Override
    public void displayInfo() {
        System.out.println("Account Type: Savings");
        System.out.println("Account Number: " + getAccountNumber());
        System.out.println("Account Holder: " + getName());
        System.out.println("Address: " + getAddress());
        System.out.println("Phone: " + getPhone());
        System.out.println("Email: " + getEmail());
        System.out.println("Balance: $" + String.format("%.2f", getBalance()));
        System.out.println("Interest Rate: " + interestRate + "%");
    }

    @Override
    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public double calculateInterest() {
        return balance * (interestRate / 100) / 12; // Monthly interest
    }

    public double getInterestRate() { return interestRate; }
}

// Checking account class
class CheckingAccount extends BankAccount {
    private double overdraftLimit;

    public CheckingAccount(int accountNumber, String name, String address, String phone, String email, double balance, double overdraftLimit) {
        super(accountNumber, name, address, phone, email, balance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void displayInfo() {
        System.out.println("Account Type: Checking");
        System.out.println("Account Number: " + getAccountNumber());
        System.out.println("Account Holder: " + getName());
        System.out.println("Address: " + getAddress());
        System.out.println("Phone: " + getPhone());
        System.out.println("Email: " + getEmail());
        System.out.println("Balance: $" + String.format("%.2f", getBalance()));
        System.out.println("Overdraft Limit: $" + String.format("%.2f", overdraftLimit));
    }

    @Override
    public boolean withdraw(double amount) {
        if (balance + overdraftLimit >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public double getOverdraftLimit() { return overdraftLimit; }
}

// Transaction class
class Transaction {
    private int accountNumber;
    private String type;
    private double amount;
    private String description;
    private LocalDateTime date;

    public Transaction(int accountNumber, String type, double amount, String description, LocalDateTime date) {
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    // Getters
    public int getAccountNumber() { return accountNumber; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public String getDescription() { return description; }
    public LocalDateTime getDate() { return date; }
}
