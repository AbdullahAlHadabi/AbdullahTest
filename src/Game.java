// FATHER CLASS HER :)
class Vehicle {
    protected int speed;
    protected int fuel;

    public Vehicle(int speed, int fuel) {
        this.speed = speed;
        this.fuel = fuel;
    }

    public void move() {
        System.out.println(" vehicle is moving " );
    }

}


class Car extends Vehicle {
    public Car(int speed, int fuel) {
        super(speed, fuel);
    }

    public void move() {
        System.out.println(" car is speed "+speed+ "  And use fuel type : " +fuel );
    }
}

class Plane extends Vehicle {

    public Plane(int speed, int fuel) {
        super(speed, fuel);
    }

    public void move() {
        System.out.println("Plane is flying and speed is :" + speed + " using fuel type : " + fuel);
    }
}


public class Game {
    public static void main(String[] args) {

        Car car = new Car(300, 500);
        Plane plane = new Plane(900, 500);

        car.move();   // inherited
        car.move();   // own method

        plane.move(); // inherited
        plane.move();  // own method
    }
}









