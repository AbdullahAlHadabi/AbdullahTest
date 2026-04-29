import java.util.ArrayList;
import java.util.Scanner;

public class ECommerceSystem {
    private static ArrayList<Product> products = new ArrayList<>();
    private static ArrayList<Customer> customers = new ArrayList<>();
    private static ArrayList<Order> orders = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== E-Commerce System ===");
        System.out.println("Built with OOP Principles\n");

        // Initialize sample data
        initializeSampleData();

        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    viewProducts();
                    break;
                case 2:
                    addProduct();
                    break;
                case 3:
                    searchProducts();
                    break;
                case 4:
                    addCustomer();
                    break;
                case 5:
                    placeOrder();
                    break;
                case 6:
                    viewOrders();
                    break;
                case 7:
                    generateReport();
                    break;
                case 8:
                    System.out.println("Thank you for using E-Commerce System!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n=== E-Commerce Menu ===");
        System.out.println("1. View Products");
        System.out.println("2. Add Product");
        System.out.println("3. Search Products");
        System.out.println("4. Add Customer");
        System.out.println("5. Place Order");
        System.out.println("6. View Orders");
        System.out.println("7. Generate Report");
        System.out.println("8. Exit");
        System.out.println("=======================");
    }

    private static void initializeSampleData() {
        // Add sample products
        products.add(new Product(1, "Laptop", "High-performance laptop", 999.99, "Electronics", 10));
        products.add(new Product(2, "Mouse", "Wireless mouse", 29.99, "Electronics", 50));
        products.add(new Product(3, "Book", "Programming book", 49.99, "Books", 25));

        // Add sample customers
        customers.add(new Customer(1, "John Doe", "john@email.com", "123 Main St"));
        customers.add(new Customer(2, "Jane Smith", "jane@email.com", "456 Oak Ave"));
    }

    private static void viewProducts() {
        System.out.println("\n=== Products ===");
        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }

        for (Product product : products) {
            System.out.println("ID: " + product.getId());
            System.out.println("Name: " + product.getName());
            System.out.println("Description: " + product.getDescription());
            System.out.println("Price: $" + product.getPrice());
            System.out.println("Category: " + product.getCategory());
            System.out.println("Stock: " + product.getStock());
            System.out.println("-------------------");
        }
    }

    private static void addProduct() {
        System.out.println("\n=== Add Product ===");
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();

        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        double price = getDoubleInput("Enter price: ");

        System.out.print("Enter category: ");
        String category = scanner.nextLine();

        int stock = getIntInput("Enter stock quantity: ");

        int id = products.size() + 1;
        Product product = new Product(id, name, description, price, category, stock);
        products.add(product);

        System.out.println("Product added successfully!");
    }

    private static void searchProducts() {
        System.out.println("\n=== Search Products ===");
        System.out.print("Enter search term: ");
        String searchTerm = scanner.nextLine().toLowerCase();

        boolean found = false;
        for (Product product : products) {
            if (product.getName().toLowerCase().contains(searchTerm) ||
                product.getCategory().toLowerCase().contains(searchTerm)) {
                System.out.println("ID: " + product.getId() + " | " + product.getName() +
                                 " | $" + product.getPrice() + " | " + product.getCategory());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No products found matching: " + searchTerm);
        }
    }

    private static void addCustomer() {
        System.out.println("\n=== Add Customer ===");
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        int id = customers.size() + 1;
        Customer customer = new Customer(id, name, email, address);
        customers.add(customer);

        System.out.println("Customer added successfully!");
    }

    private static void placeOrder() {
        System.out.println("\n=== Place Order ===");
        if (customers.isEmpty()) {
            System.out.println("No customers available. Please add a customer first.");
            return;
        }

        // Display customers
        System.out.println("Available Customers:");
        for (Customer customer : customers) {
            System.out.println(customer.getId() + ". " + customer.getName());
        }

        int customerId = getIntInput("Select customer ID: ");
        Customer customer = findCustomerById(customerId);
        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        // Create order
        Order order = new Order(orders.size() + 1, customer);

        boolean addingItems = true;
        while (addingItems) {
            viewProducts();
            int productId = getIntInput("Enter product ID to add (0 to finish): ");
            if (productId == 0) break;

            Product product = findProductById(productId);
            if (product == null) {
                System.out.println("Product not found.");
                continue;
            }

            int quantity = getIntInput("Enter quantity: ");
            if (quantity > product.getStock()) {
                System.out.println("Insufficient stock. Available: " + product.getStock());
                continue;
            }

            order.addItem(new OrderItem(product, quantity));
            product.setStock(product.getStock() - quantity);

            System.out.print("Add another item? (y/n): ");
            String choice = scanner.nextLine().toLowerCase();
            if (!choice.equals("y") && !choice.equals("yes")) {
                addingItems = false;
            }
        }

        if (!order.getItems().isEmpty()) {
            orders.add(order);
            System.out.println("Order placed successfully! Total: $" + order.getTotal());
        }
    }

    private static void viewOrders() {
        System.out.println("\n=== Orders ===");
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
            return;
        }

        for (Order order : orders) {
            System.out.println("Order ID: " + order.getId());
            System.out.println("Customer: " + order.getCustomer().getName());
            System.out.println("Items:");
            for (OrderItem item : order.getItems()) {
                System.out.println("  - " + item.getProduct().getName() + " x" +
                                 item.getQuantity() + " = $" + item.getSubtotal());
            }
            System.out.println("Total: $" + order.getTotal());
            System.out.println("-------------------");
        }
    }

    private static void generateReport() {
        System.out.println("\n=== Sales Report ===");
        System.out.println("Total Products: " + products.size());
        System.out.println("Total Customers: " + customers.size());
        System.out.println("Total Orders: " + orders.size());

        double totalRevenue = 0;
        for (Order order : orders) {
            totalRevenue += order.getTotal();
        }
        System.out.println("Total Revenue: $" + String.format("%.2f", totalRevenue));

        // Category breakdown
        System.out.println("\nProducts by Category:");
        // This would be implemented in a future commit
    }

    private static Product findProductById(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    private static Customer findCustomerById(int id) {
        for (Customer customer : customers) {
            if (customer.getId() == id) {
                return customer;
            }
        }
        return null;
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

// Product class
class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private String category;
    private int stock;

    public Product(int id, String name, String description, double price, String category, int stock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.stock = stock;
    }

    // Getters and setters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}

// Customer class
class Customer {
    private int id;
    private String name;
    private String email;
    private String address;

    public Customer(int id, String name, String email, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
}

// Order class
class Order {
    private int id;
    private Customer customer;
    private ArrayList<OrderItem> items;

    public Order(int id, Customer customer) {
        this.id = id;
        this.customer = customer;
        this.items = new ArrayList<>();
    }

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public double getTotal() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    // Getters
    public int getId() { return id; }
    public Customer getCustomer() { return customer; }
    public ArrayList<OrderItem> getItems() { return items; }
}

// OrderItem class
class OrderItem {
    private Product product;
    private int quantity;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public double getSubtotal() {
        return product.getPrice() * quantity;
    }

    // Getters
    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
}
