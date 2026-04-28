import java.util.Scanner;
import java.util.ArrayList;
import java.util.Stack;

public class AdvancedCalculator {
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<String> history = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("=== Advanced Calculator ===");
        System.out.println("Built with OOP Principles\n");

        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    performBasicCalculation();
                    break;
                case 2:
                    performScientificCalculation();
                    break;
                case 3:
                    viewHistory();
                    break;
                case 4:
                    clearHistory();
                    break;
                case 5:
                    System.out.println("Thank you for using Advanced Calculator!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n=== Calculator Menu ===");
        System.out.println("1. Basic Calculations (+, -, *, /)");
        System.out.println("2. Scientific Calculations (sin, cos, tan, log, sqrt)");
        System.out.println("3. View Calculation History");
        System.out.println("4. Clear History");
        System.out.println("5. Exit");
        System.out.println("========================");
    }

    private static void performBasicCalculation() {
        System.out.println("\n=== Basic Calculation ===");
        double num1 = getDoubleInput("Enter first number: ");
        System.out.print("Enter operator (+, -, *, /): ");
        String operator = scanner.nextLine();

        double num2 = getDoubleInput("Enter second number: ");

        double result = 0;
        boolean validOperation = true;

        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                if (num2 != 0) {
                    result = num1 / num2;
                } else {
                    System.out.println("Error: Division by zero!");
                    validOperation = false;
                }
                break;
            default:
                System.out.println("Invalid operator!");
                validOperation = false;
        }

        if (validOperation) {
            System.out.println("Result: " + result);
            String calculation = num1 + " " + operator + " " + num2 + " = " + result;
            history.add(calculation);
        }
    }

    private static void performScientificCalculation() {
        System.out.println("\n=== Scientific Calculation ===");
        System.out.println("Available functions:");
        System.out.println("1. Sine (sin)");
        System.out.println("2. Cosine (cos)");
        System.out.println("3. Tangent (tan)");
        System.out.println("4. Natural Log (ln)");
        System.out.println("5. Log base 10 (log10)");
        System.out.println("6. Square Root (sqrt)");
        System.out.println("7. Power (x^y)");

        int choice = getIntInput("Select function: ");
        double num = getDoubleInput("Enter number: ");
        double result = 0;
        String function = "";

        switch (choice) {
            case 1:
                result = Math.sin(Math.toRadians(num));
                function = "sin(" + num + "°)";
                break;
            case 2:
                result = Math.cos(Math.toRadians(num));
                function = "cos(" + num + "°)";
                break;
            case 3:
                result = Math.tan(Math.toRadians(num));
                function = "tan(" + num + "°)";
                break;
            case 4:
                if (num > 0) {
                    result = Math.log(num);
                    function = "ln(" + num + ")";
                } else {
                    System.out.println("Error: Natural log requires positive number!");
                    return;
                }
                break;
            case 5:
                if (num > 0) {
                    result = Math.log10(num);
                    function = "log10(" + num + ")";
                } else {
                    System.out.println("Error: Log requires positive number!");
                    return;
                }
                break;
            case 6:
                if (num >= 0) {
                    result = Math.sqrt(num);
                    function = "sqrt(" + num + ")";
                } else {
                    System.out.println("Error: Square root requires non-negative number!");
                    return;
                }
                break;
            case 7:
                double exponent = getDoubleInput("Enter exponent: ");
                result = Math.pow(num, exponent);
                function = num + "^" + exponent;
                break;
            default:
                System.out.println("Invalid choice!");
                return;
        }

        System.out.println("Result: " + result);
        String calculation = function + " = " + result;
        history.add(calculation);
    }

    private static void viewHistory() {
        System.out.println("\n=== Calculation History ===");
        if (history.isEmpty()) {
            System.out.println("No calculations in history.");
        } else {
            for (int i = 0; i < history.size(); i++) {
                System.out.println((i + 1) + ". " + history.get(i));
            }
        }
    }

    private static void clearHistory() {
        history.clear();
        System.out.println("Calculation history cleared.");
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
