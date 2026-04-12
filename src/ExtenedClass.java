import java.lang.invoke.MutableCallSite;

public class ExtenedClass {
    public static void main(String[] args) {

        Manufature m1 = new SuperCar("Bmw",23,"black");
        Manufature m2 = new Truck("Volvo", 2020, "white");

        m1.display();
        m2.display();
    }
}
// Father class
class Manufature {
    protected String brand;
    protected int year;

    public Manufature(String brand, int year) {
        this.brand = brand;
        this.year = year;
    }

    public void display() {
        System.out.println("brand: " + brand);
        System.out.println("year: " + year);
    }
}
class Truck extends Manufature {
    private String color;

    public Truck(String brand, int year, String color) {
        super(brand, year);
        this.color = color;
    }

    public void display() {
        System.out.println("color: " + color);
        System.out.println("brand: " + brand);
        System.out.println("year: " + year);
    }

}
class SuperCar extends Manufature {
    private String color;

    public SuperCar(String brand, int year, String color) {
        super(brand, year);
        this.color = color;
    }

    public void display() {
        System.out.println("color: " + color);
        System.out.println("brand: " + brand);
        System.out.println("year: " + year);

    }
}






