import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.*;

public class BankAccountSimulator {
    private static ArrayList<SimAccount> accounts = new ArrayList<>();
    private static ArrayList<SimTransaction> transactions = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    static int accountIdCounter = 1000;

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
        System.out.println("\n=== Banking System Menu ===");
        System.out.println("1. Create New Account");
        System.out.println("2. Deposit Money");
        System.out.println("3. Withdraw Money");
        System.out.println("4. Transfer Money");
        System.out.println("5. View Account Details");
        System.out.println("6. View Transaction History");
        System.out.println("7. Generate Report");
        System.out.println("8. Exit");
        System.out.println("===========================");
    }

    private static void createAccount() {
        System.out.println("\n=== Create New Account ===");
        System.out.println("1. Savings Account");
        System.out.println("2. Checking Account");
        int typeChoice = getIntInput("Enter account type: ");

        System.out.print("Enter account holder name: ");
        String name = scanner.nextLine();
        System.out.print("Enter initial deposit: ");
        double initialDeposit = getDoubleInput("");

        if (initialDeposit < 0) {
            System.out.println("Initial deposit cannot be negative.");
            return;
        }

        SimAccount account;
        if (typeChoice == 1) {
            System.out.print("Enter interest rate (%): ");
            double interestRate = getDoubleInput("");
            account = new SimSavingsAccount(name, initialDeposit, interestRate);
        } else {
            account = new SimCheckingAccount(name, initialDeposit);
        }

        accounts.add(account);

        // Record initial deposit transaction
        SimTransaction transaction = new SimTransaction(account.getAccountNumber(), "Initial Deposit",
                                                initialDeposit, account.getBalance(), "Deposit");
        transactions.add(transaction);

        System.out.println("Account created successfully!");
        System.out.println("Account Number: " + account.getAccountNumber());
        System.out.println("Account Type: " + account.getAccountType());
    }

    private static void depositMoney() {
        System.out.println("\n=== Deposit Money ===");
        int accountNumber = getIntInput("Enter account number: ");

        SimAccount account = findAccountByNumber(accountNumber);
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
        SimTransaction transaction = new SimTransaction(accountNumber, "Deposit", amount,
                                                account.getBalance(), "Deposit");
        transactions.add(transaction);

        System.out.println("Deposit successful!");
        System.out.println("Previous Balance: $" + String.format("%.2f", oldBalance));
        System.out.println("New Balance: $" + String.format("%.2f", account.getBalance()));
    }

    private static void withdrawMoney() {
        System.out.println("\n=== Withdraw Money ===");
        int accountNumber = getIntInput("Enter account number: ");

        SimAccount account = findAccountByNumber(accountNumber);
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
            SimTransaction transaction = new SimTransaction(accountNumber, "Withdrawal", amount,
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

        SimAccount fromAccount = findAccountByNumber(fromAccountNumber);
        if (fromAccount == null) {
            System.out.println("Sender account not found.");
            return;
        }

        int toAccountNumber = getIntInput("Enter receiver account number: ");

        SimAccount toAccount = findAccountByNumber(toAccountNumber);
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
            SimTransaction debitTransaction = new SimTransaction(fromAccountNumber,
                "Transfer to " + toAccountNumber, amount, fromAccount.getBalance(), "Transfer Out");
            SimTransaction creditTransaction = new SimTransaction(toAccountNumber,
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

        SimAccount account = findAccountByNumber(accountNumber);
        if (account == null) {
            System.out.println("Account not found.");
            return;
        }

        account.displayAccountInfo();

        // Show additional details for savings accounts
        if (account instanceof SimSavingsAccount) {
            SimSavingsAccount savings = (SimSavingsAccount) account;
            System.out.println("Interest Rate: " + savings.getInterestRate() + "%");
        } else if (account instanceof SimCheckingAccount) {
            SimCheckingAccount checking = (SimCheckingAccount) account;
            System.out.println("Overdraft Limit: $" + checking.getOverdraftLimit());
        }
    }

    private static void viewTransactionHistory() {
        System.out.println("\n=== Transaction History ===");
        int accountNumber = getIntInput("Enter account number: ");

        ArrayList<SimTransaction> accountTransactions = new ArrayList<>();
        for (SimTransaction transaction : transactions) {
            if (transaction.getAccountNumber() == accountNumber) {
                accountTransactions.add(transaction);
            }
        }

        if (accountTransactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        System.out.println("\nTransaction History:");
        System.out.println("--------------------------------------------");
        for (int i = accountTransactions.size() - 1; i >= 0; i--) {
            SimTransaction transaction = accountTransactions.get(i);
            System.out.println(transaction.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                             " | " + transaction.getType() +
                             " | $" + String.format("%.2f", transaction.getAmount()) +
                             " | Balance: $" + String.format("%.2f", transaction.getBalanceAfter()));
        }
    }

    private static void generateAccountReport() {
        System.out.println("\n=== Account Report ===");
        System.out.println("Total Accounts: " + accounts.size());
        System.out.println("Total Transactions: " + transactions.size());

        double totalBalance = 0;
        int savingsCount = 0;
        int checkingCount = 0;

        for (SimAccount account : accounts) {
            totalBalance += account.getBalance();
            if (account instanceof SimSavingsAccount) {
                savingsCount++;
            } else if (account instanceof SimCheckingAccount) {
                checkingCount++;
            }
        }

        System.out.println("Savings Accounts: " + savingsCount);
        System.out.println("Checking Accounts: " + checkingCount);
        System.out.println("Total Balance: $" + String.format("%.2f", totalBalance));
    }

    private static SimAccount findAccountByNumber(int accountNumber) {
        for (SimAccount account : accounts) {
            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }
        return null;
    }

    private static void loadDataFromFiles() {
        File accountsFile = new File("bank_accounts_sim.txt");
        if (accountsFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(accountsFile))) {
                String line;
                reader.readLine(); // Skip header
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 4) {
                        int accountNumber = Integer.parseInt(parts[0]);
                        String holder = parts[1];
                        double balance = Double.parseDouble(parts[2]);
                        String type = parts[3];

                        SimAccount account;
                        if (type.equals("Savings")) {
                            double rate = Double.parseDouble(parts[4]);
                            account = new SimSavingsAccount(holder, balance, rate);
                        } else {
                            account = new SimCheckingAccount(holder, balance);
                        }
                        account.setAccountNumber(accountNumber);
                        accounts.add(account);

                        if (accountNumber >= accountIdCounter) {
                            accountIdCounter = accountNumber + 1;
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Error loading accounts: " + e.getMessage());
            }
        }

        File transactionsFile = new File("bank_transactions_sim.txt");
        if (transactionsFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(transactionsFile))) {
                String line;
                reader.readLine(); // Skip header
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 6) {
                        int accountNumber = Integer.parseInt(parts[0]);
                        String description = parts[1];
                        double amount = Double.parseDouble(parts[2]);
                        double balanceAfter = Double.parseDouble(parts[3]);
                        String type = parts[4];
                        LocalDateTime timestamp = LocalDateTime.parse(parts[5]);

                        SimTransaction transaction = new SimTransaction(accountNumber, description, amount, balanceAfter, type);
                        transaction.setTimestamp(timestamp);
                        transactions.add(transaction);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error loading transactions: " + e.getMessage());
            }
        }
    }

    private static void saveDataToFiles() {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream("bank_accounts_sim.txt"))) {
            writer.println("AccountNumber,Holder,Balance,Type,Rate");
            for (SimAccount account : accounts) {
                writer.print(account.getAccountNumber() + ",");
                writer.print(account.getAccountHolder() + ",");
                writer.print(account.getBalance() + ",");
                writer.print(account.getAccountType());
                if (account instanceof SimSavingsAccount) {
                    writer.print("," + ((SimSavingsAccount) account).getInterestRate());
                }
                writer.println();
            }
        } catch (IOException e) {
            System.out.println("Error saving accounts: " + e.getMessage());
        }

        try (PrintWriter writer = new PrintWriter(new FileOutputStream("bank_transactions_sim.txt"))) {
            writer.println("AccountNumber,Description,Amount,BalanceAfter,Type,Timestamp");
            for (SimTransaction transaction : transactions) {
                writer.println(transaction.getAccountNumber() + "," +
                             transaction.getDescription() + "," +
                             transaction.getAmount() + "," +
                             transaction.getBalanceAfter() + "," +
                             transaction.getType() + "," +
                             transaction.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
        } catch (IOException e) {
            System.out.println("Error saving transactions: " + e.getMessage());
        }
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}

// Abstract SimAccount class demonstrating inheritance and abstraction
abstract class SimAccount {
    protected int accountNumber;
    protected String accountHolder;
    protected double balance;

    public SimAccount(String accountHolder, double initialBalance) {
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

// SimSavingsAccount class demonstrating inheritance
class SimSavingsAccount extends SimAccount {
    private double interestRate;

    public SimSavingsAccount(String accountHolder, double initialBalance, double interestRate) {
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
        double interest = balance * (interestRate / 100) / 12;
        balance += interest;
    }
}

// SimCheckingAccount class demonstrating inheritance
class SimCheckingAccount extends SimAccount {
    private static final double OVERDRAFT_LIMIT = 500.0;

    public SimCheckingAccount(String accountHolder, double initialBalance) {
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

// SimTransaction class for recording account activities
class SimTransaction {
    private int accountNumber;
    private String description;
    private double amount;
    private double balanceAfter;
    private String type;
    private LocalDateTime timestamp;

    public SimTransaction(int accountNumber, String description, double amount, double balanceAfter, String type) {
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