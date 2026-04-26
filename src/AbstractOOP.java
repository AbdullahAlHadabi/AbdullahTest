public class AbstractOOP {
    public static void main(String[] args) {
        // Create different shapes using polymorphism
        Shape circle = new Circle(5.0);
        Shape rectangle = new Rectangle(4.0, 6.0);
        Shape triangle = new Triangle(3.0, 4.0, 5.0);

        // Store shapes in an array to demonstrate polymorphism
        Shape[] shapes = {circle, rectangle, triangle};

        System.out.println("=== Shape Calculator ===\n");

        // Calculate and display properties for each shape
        for (Shape shape : shapes) {
            System.out.println(shape.getClass().getSimpleName() + ":");
            shape.displayInfo();
            System.out.println("Area: " + String.format("%.2f", shape.area()));
            System.out.println("Perimeter: " + String.format("%.2f", shape.perimeter()));
            System.out.println();
        }
    }
}
abstract class Shape {
    // Abstract methods that all shapes must implement
    abstract double area();
    abstract double perimeter();

    // Common method for displaying shape information
    void displayInfo() {
        System.out.println("  Type: " + this.getClass().getSimpleName());
    }
}

class Circle extends Shape {
    private double radius;

    Circle(double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be positive");
        }
        this.radius = radius;
    }

    double getRadius() {
        return radius;
    }

    void setRadius(double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be positive");
        }
        this.radius = radius;
    }

    @Override
    double area() {
        return Math.PI * radius * radius;
    }

    @Override
    double perimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    void displayInfo() {
        super.displayInfo();
        System.out.println("  Radius: " + radius);
    }
}

class Rectangle extends Shape {
    private double length;
    private double width;

    Rectangle(double length, double width) {
        if (length <= 0 || width <= 0) {
            throw new IllegalArgumentException("Length and width must be positive");
        }
        this.length = length;
        this.width = width;
    }

    double getLength() {
        return length;
    }

    void setLength(double length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be positive");
        }
        this.length = length;
    }

    double getWidth() {
        return width;
    }

    void setWidth(double width) {
        if (width <= 0) {
            throw new IllegalArgumentException("Width must be positive");
        }
        this.width = width;
    }

    @Override
    double area() {
        return length * width;
    }

    @Override
    double perimeter() {
        return 2 * (length + width);
    }

    @Override
    void displayInfo() {
        super.displayInfo();
        System.out.println("  Length: " + length);
        System.out.println("  Width: " + width);
    }
}

class Triangle extends Shape {
    private double sideA;
    private double sideB;
    private double sideC;

    Triangle(double sideA, double sideB, double sideC) {
        if (sideA <= 0 || sideB <= 0 || sideC <= 0) {
            throw new IllegalArgumentException("All sides must be positive");
        }
        // Triangle inequality check
        if (sideA + sideB <= sideC || sideA + sideC <= sideB || sideB + sideC <= sideA) {
            throw new IllegalArgumentException("Invalid triangle sides");
        }
        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
    }

    // Getters
    double getSideA() { return sideA; }
    double getSideB() { return sideB; }
    double getSideC() { return sideC; }

    @Override
    double area() {
        // Heron's formula
        double s = perimeter() / 2;
        return Math.sqrt(s * (s - sideA) * (s - sideB) * (s - sideC));
    }

    @Override
    double perimeter() {
        return sideA + sideB + sideC;
    }

    @Override
    void displayInfo() {
        super.displayInfo();
        System.out.println("  Side A: " + sideA);
        System.out.println("  Side B: " + sideB);
        System.out.println("  Side C: " + sideC);
    }
}