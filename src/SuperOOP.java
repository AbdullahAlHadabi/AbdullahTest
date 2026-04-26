public class SuperOOP {
    public static void main(String[] args) {
        System.out.println("=== Inheritance and Super Keyword Demo ===\n");

        // Create a School object (which inherits from Details)
        School school = new School("Green Valley High School", 1995, "Springfield", "John Smith", 45, "Springfield");

        System.out.println("1. School Information (using overridden method):");
        school.displayInfo(); // This calls the overridden method

        System.out.println("\n2. Calling parent class method directly:");
        school.displayParentInfo(); // This calls the parent's displayInfo method

        System.out.println("\n3. Using getter methods:");
        System.out.println("School Name: " + school.getSchoolName());
        System.out.println("Established Year: " + school.getEstablishedYear());

        System.out.println("\n4. Inheritance benefits demonstration:");
        school.showInheritanceBenefits();

        System.out.println("\n5. Polymorphism example:");
        Details person = school; // School IS-A Details (polymorphism)
        System.out.println("Treating School as Details:");
        person.displayInfo(); // Calls School's overridden method

        System.out.println("\n=== Summary: Why super keyword is important ===");
        System.out.println("✓ super() - Calls parent constructor to initialize inherited fields");
        System.out.println("✓ super.method() - Accesses parent methods even when overridden");
        System.out.println("✓ super.field - Accesses parent fields when names conflict");
        System.out.println("✓ Enables code reuse and proper inheritance hierarchy");
    }
}

class Details {
    String name;
    int age;
    String city;

    public Details(String name, int age, String city) {
        this.name = name;
        this.age = age;
        this.city = city;
    }

    void displayInfo() {
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("City: " + city);
    }
}

class School extends Details {
    String schoolName;
    int establishedYear;

    // Constructor using super() to call parent constructor
    public School(String schoolName, int establishedYear, String city, String principalName, int principalAge, String principalCity) {
        // super() MUST be the first statement in constructor
        // It calls the parent class constructor
        super(principalName, principalAge, principalCity);
        // Now initialize School-specific fields
        this.schoolName = schoolName;
        this.establishedYear = establishedYear;
    }

    // Overriding the parent method
    @Override
    void displayInfo() {
        System.out.println("School Name: " + schoolName);
        System.out.println("Established: " + establishedYear);
        System.out.println("Location: " + city); // Inherited field

        // Using super to call parent method for principal info
        System.out.println("\nPrincipal Details:");
        super.displayInfo(); // Calls the parent's displayInfo method
    }

    // Method to demonstrate accessing parent method directly
    void displayParentInfo() {
        super.displayInfo(); // Explicitly call parent method
    }

    // Method to show inheritance benefits
    void showInheritanceBenefits() {
        System.out.println("School inherits:");
        System.out.println("- name, age, city fields from Details");
        System.out.println("- displayInfo() method from Details");
        System.out.println("- And adds its own schoolName and establishedYear");
    }

    // Getter methods
    String getSchoolName() {
        return schoolName;
    }

    int getEstablishedYear() {
        return establishedYear;
    }

    // Method using super to access parent fields
    void compareLocations() {
        System.out.println("School city: " + city); // Inherited field
        System.out.println("Principal city: " + super.city); // Same field, accessed via super
    }
}
