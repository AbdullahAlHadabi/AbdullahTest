import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.*;

public class BankAccountSimulator {
    private static ArrayList<Account> accounts = new ArrayList<>();
    private static ArrayList<Transaction> transactions = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static int accountIdCounter = 1000;

    public static void main(String[] args) {
        System.out.println("=== Bank Account Simulator ===");
        System.out.println("Built with OOP Principles\n");

        // Load data from files
        loadDataFromFiles();

        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    depositMoney();
                    break;
                case 3:
                    withdrawMoney();
                    break;
                case 4:
                    transferMoney();
                    break;
                case 5:
                    viewAccountDetails();
                    break;
                case 6:
                    viewTransactionHistory();
                    break;
                case 7:
                    generateAccountReport();
                    break;
                case 8:
                    saveDataToFiles();
                    System.out.println("Thank you for using Bank Account Simulator!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n=== Bank Account Menu ===");
        System.out.println("1. Create New Account");
        System.out.println("2. Deposit Money");
        System.out.println("3. Withdraw Money");
        System.out.println("4. Transfer Money");
        System.out.println("5. View Account Details");
        System.out.println("6. View Transaction History");
        System.out.println("7. Generate Account Report");
        System.out.println("8. Exit");
        System.out.println("==========================");
    }

    private static void createAccount() {
        System.out.println("\n=== Create New Account ===");
        System.out.println("Account Types:");
        System.out.println("1. Savings Account");
        System.out.println("2. Checking Account");

        int typeChoice = getIntInput("Select account type: ");
        System.out.print("Enter account holder name: ");
        String name = scanner.nextLine();

        System.out.print("Enter initial deposit amount: ");
        double initialDeposit = getDoubleInput("");

        Account account;
        if (typeChoice == 1) {
            System.out.print("Enter interest rate (%): ");
            double interestRate = getDoubleInput("");
            account = new SavingsAccount(name, initialDeposit, interestRate);
        } else {
            account = new CheckingAccount(name, initialDeposit);
        }

        accounts.add(account);

        // Record initial deposit transaction
        Transaction transaction = new Transaction(account.getAccountNumber(), "Initial Deposit",
                                                initialDeposit, account.getBalance(), "Deposit");
        transactions.add(transaction);

        System.out.println("Account created successfully!");
        System.out.println("Account Number: " + account.getAccountNumber());
        System.out.println("Account Type: " + account.getAccountType());
    }

    private static void depositMoney() {
        System.out.println("\n=== Deposit Money ===");
        int accountNumber = getIntInput("Enter account number: ");

        Account account = findAccountByNumber(accountNumber);
        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        double amount = getDoubleInput("Enter deposit amount: ");
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive.");
            return;
        }

        double oldBalance = account.getBalance();
        account.deposit(amount);

        // Record transaction
        Transaction transaction = new Transaction(accountNumber, "Deposit", amount,
                                                account.getBalance(), "Deposit");
        transactions.add(transaction);

        System.out.println("Deposit successful!");
        System.out.println("Previous Balance: $" + String.format("%.2f", oldBalance));
        System.out.println("New Balance: $" + String.format("%.2f", account.getBalance()));
    }

    private static void withdrawMoney() {
        System.out.println("\n=== Withdraw Money ===");
        int accountNumber = getIntInput("Enter account number: ");

        Account account = findAccountByNumber(accountNumber);
        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        double amount = getDoubleInput("Enter withdrawal amount: ");
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
            return;
        }

        double oldBalance = account.getBalance();
        boolean success = account.withdraw(amount);

        if (success) {
            // Record transaction
            Transaction transaction = new Transaction(accountNumber, "Withdrawal", amount,
                                                    account.getBalance(), "Withdrawal");
            transactions.add(transaction);

            System.out.println("Withdrawal successful!");
            System.out.println("Previous Balance: $" + String.format("%.2f", oldBalance));
            System.out.println("New Balance: $" + String.format("%.2f", account.getBalance()));
        } else {
            System.out.println("Insufficient funds or withdrawal limit exceeded.");
        }
    }

    private static void transferMoney() {
        System.out.println("\n=== Transfer Money ===");
        int fromAccountNumber = getIntInput("Enter sender account number: ");

        Account fromAccount = findAccountByNumber(fromAccountNumber);
        if (fromAccount == null) {
            System.out.println("Sender account not found.");
            return;
        }

        int toAccountNumber = getIntInput("Enter receiver account number: ");

        Account toAccount = findAccountByNumber(toAccountNumber);
        if (toAccount == null) {
            System.out.println("Receiver account not found.");
            return;
        }

        if (fromAccountNumber == toAccountNumber) {
            System.out.println("Cannot transfer to the same account.");
            return;
        }

        double amount = getDoubleInput("Enter transfer amount: ");
        if (amount <= 0) {
            System.out.println("Transfer amount must be positive.");
            return;
        }

        boolean success = fromAccount.withdraw(amount);
        if (success) {
            toAccount.deposit(amount);

            // Record transactions
            Transaction debitTransaction = new Transaction(fromAccountNumber,
                "Transfer to " + toAccountNumber, amount, fromAccount.getBalance(), "Transfer Out");
            Transaction creditTransaction = new Transaction(toAccountNumber,
                "Transfer from " + fromAccountNumber, amount, toAccount.getBalance(), "Transfer In");

            transactions.add(debitTransaction);
            transactions.add(creditTransaction);

            System.out.println("Transfer successful!");
            System.out.println("Transferred: $" + String.format("%.2f", amount));
            System.out.println("From Account Balance: $" + String.format("%.2f", fromAccount.getBalance()));
            System.out.println("To Account Balance: $" + String.format("%.2f", toAccount.getBalance()));
        } else {
            System.out.println("Transfer failed. Insufficient funds.");
        }
    }

    private static void viewAccountDetails() {
        System.out.println("\n=== Account Details ===");
        int accountNumber = getIntInput("Enter account number: ");

        Account account = findAccountByNumber(accountNumber);
        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        account.displayAccountInfo();
    }

    private static void viewTransactionHistory() {
        System.out.println("\n=== Transaction History ===");
        int accountNumber = getIntInput("Enter account number: ");

        Account account = findAccountByNumber(accountNumber);
        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        System.out.println("Transaction History for Account: " + accountNumber);
        boolean hasTransactions = false;

        for (Transaction transaction : transactions) {
            if (transaction.getAccountNumber() == accountNumber) {
                System.out.println(transaction.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                                 " | " + transaction.getDescription() + " | $" +
                                 String.format("%.2f", transaction.getAmount()) + " | Balance: $" +
                                 String.format("%.2f", transaction.getBalanceAfter()));
                hasTransactions = true;
            }
        }

        if (!hasTransactions) {
            System.out.println("No transactions found for this account.");
        }
    }

    private static void generateAccountReport() {
        System.out.println("\n=== Account Report ===");
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }

        double totalBalance = 0;
        int savingsCount = 0;
        int checkingCount = 0;

        System.out.println("Individual Account Summary:");
        System.out.println("----------------------------");

        for (Account account : accounts) {
            System.out.println("Account: " + account.getAccountNumber() + " (" + account.getAccountType() + ")");
            System.out.println("Holder: " + account.getAccountHolder());
            System.out.println("Balance: $" + String.format("%.2f", account.getBalance()));

            if (account instanceof SavingsAccount) {
                SavingsAccount savings = (SavingsAccount) account;
                System.out.println("Interest Rate: " + savings.getInterestRate() + "%");
                savingsCount++;
            } else if (account instanceof CheckingAccount) {
                checkingCount++;
            }

            totalBalance += account.getBalance();
            System.out.println();
        }

        System.out.println("Bank Summary:");
        System.out.println("Total Accounts: " + accounts.size());
        System.out.println("Savings Accounts: " + savingsCount);
        System.out.println("Checking Accounts: " + checkingCount);
        System.out.println("Total Bank Balance: $" + String.format("%.2f", totalBalance));
        System.out.println("Average Account Balance: $" + String.format("%.2f", totalBalance / accounts.size()));
    }

    private static Account findAccountByNumber(int accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }
        return null;
    }

    private static void saveDataToFiles() {
        // Save accounts
        try (PrintWriter writer = new PrintWriter(new FileWriter("accounts.txt"))) {
            for (Account account : accounts) {
                String accountType = account instanceof SavingsAccount ? "SAVINGS" : "CHECKING";
                writer.println(account.getAccountNumber() + "," + account.getAccountHolder() + "," +
                             account.getBalance() + "," + accountType);

                if (account instanceof SavingsAccount) {
                    SavingsAccount savings = (SavingsAccount) account;
                    writer.println("INTEREST_RATE," + savings.getInterestRate());
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving accounts: " + e.getMessage());
        }

        // Save transactions
        try (PrintWriter writer = new PrintWriter(new FileWriter("transactions.txt"))) {
            for (Transaction transaction : transactions) {
                writer.println(transaction.getAccountNumber() + "," + transaction.getDescription() + "," +
                             transaction.getAmount() + "," + transaction.getBalanceAfter() + "," +
                             transaction.getType() + "," + transaction.getTimestamp());
            }
        } catch (IOException e) {
            System.out.println("Error saving transactions: " + e.getMessage());
        }

        System.out.println("Data saved successfully!");
    }

    private static void loadDataFromFiles() {
        // Load accounts
        try (BufferedReader reader = new BufferedReader(new FileReader("accounts.txt"))) {
            String line;
            Account currentAccount = null;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4 && !parts[0].equals("INTEREST_RATE")) {
                    int accountNumber = Integer.parseInt(parts[0]);
                    String holder = parts[1];
                    double balance = Double.parseDouble(parts[2]);
                    String accountType = parts[3];

                    if (accountType.equals("SAVINGS")) {
                        // Read next line for interest rate
                        String interestLine = reader.readLine();
                        if (interestLine != null && interestLine.startsWith("INTEREST_RATE,")) {
                            double interestRate = Double.parseDouble(interestLine.split(",")[1]);
                            currentAccount = new SavingsAccount(holder, balance, interestRate);
                        }
                    } else {
                        currentAccount = new CheckingAccount(holder, balance);
                    }

                    if (currentAccount != null) {
                        currentAccount.setAccountNumber(accountNumber);
                        accounts.add(currentAccount);

                        if (accountNumber >= accountIdCounter) {
                            accountIdCounter = accountNumber + 1;
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist, start with empty list
        } catch (IOException e) {
            System.out.println("Error loading accounts: " + e.getMessage());
        }

        // Load transactions
        try (BufferedReader reader = new BufferedReader(new FileReader("transactions.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    int accountNumber = Integer.parseInt(parts[0]);
                    String description = parts[1];
                    double amount = Double.parseDouble(parts[2]);
                    double balanceAfter = Double.parseDouble(parts[3]);
                    String type = parts[4];
                    LocalDateTime timestamp = LocalDateTime.parse(parts[5]);

                    Transaction transaction = new Transaction(accountNumber, description, amount, balanceAfter, type);
                    transaction.setTimestamp(timestamp);
                    transactions.add(transaction);
                }
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist, start with empty list
        } catch (IOException e) {
            System.out.println("Error loading transactions: " + e.getMessage());
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

// Abstract Account class demonstrating inheritance and abstraction
abstract class Account {
    protected int accountNumber;
    protected String accountHolder;
    protected double balance;

    public Account(String accountHolder, double initialBalance) {
        this.accountNumber = BankAccountSimulator.accountIdCounter++;
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
    }

    public abstract String getAccountType();
    public abstract boolean withdraw(double amount);

    public void deposit(double amount) {
        balance += amount;
    }

    public void displayAccountInfo() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder: " + accountHolder);
        System.out.println("Account Type: " + getAccountType());
        System.out.println("Balance: $" + String.format("%.2f", balance));
    }

    // Getters and setters
    public int getAccountNumber() { return accountNumber; }
    public void setAccountNumber(int accountNumber) { this.accountNumber = accountNumber; }
    public String getAccountHolder() { return accountHolder; }
    public double getBalance() { return balance; }
}

// Savings Account class demonstrating inheritance
class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(String accountHolder, double initialBalance, double interestRate) {
        super(accountHolder, initialBalance);
        this.interestRate = interestRate;
    }

    @Override
    public String getAccountType() {
        return "Savings";
    }

    @Override
    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void applyInterest() {
        double interest = balance * (interestRate / 100) / 12; // Monthly interest
        balance += interest;
    }
}

// Checking Account class demonstrating inheritance
class CheckingAccount extends Account {
    private static final double OVERDRAFT_LIMIT = 500.0;

    public CheckingAccount(String accountHolder, double initialBalance) {
        super(accountHolder, initialBalance);
    }

    @Override
    public String getAccountType() {
        return "Checking";
    }

    @Override
    public boolean withdraw(double amount) {
        if (balance + OVERDRAFT_LIMIT >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public double getOverdraftLimit() {
        return OVERDRAFT_LIMIT;
    }
}

// Transaction class for recording account activities
class Transaction {
    private int accountNumber;
    private String description;
    private double amount;
    private double balanceAfter;
    private String type;
    private LocalDateTime timestamp;

    public Transaction(int accountNumber, String description, double amount, double balanceAfter, String type) {
        this.accountNumber = accountNumber;
        this.description = description;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.type = type;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and setters
    public int getAccountNumber() { return accountNumber; }
    public String getDescription() { return description; }
    public double getAmount() { return amount; }
    public double getBalanceAfter() { return balanceAfter; }
    public String getType() { return type; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
