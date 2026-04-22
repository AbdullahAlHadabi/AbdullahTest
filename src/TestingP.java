public class TestingP {
    public static void main(String[] args) {
        Person emp = new Employee(50000, 5000, "John Doe");
        emp.display();

    }
}
class Person {
    private int salary;
    private int bonus;

    public Person(int salary, int bonus) {
        this.salary = salary;
        this.bonus = bonus;
    }

    public int getSalary() {
        return salary;
    }

    public int getBonus() {
        return bonus;
    }

    protected void display() {
        System.out.println("Salary: " + salary);
        System.out.println("Bonus: " + bonus);
    }

}


class Employee extends Person {

    String name;
    public Employee(int salary, int bonus, String name) {
        super(salary, bonus);
        this.name = name;
    }

    public void display() {
        super.display();
        System.out.println("Name: " + name);
    }
}



