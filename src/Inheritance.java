public class Inheritance {

    // father class
    class Animal {
        void eat() {
            System.out.println("eating");
        }
    }

    // son class
    class Dog extends Animal {
        void bark() {
            System.out.println("barking");
        }
    }

    public static void main(String[] args) {
        Inheritance i = new Inheritance();
        Dog d = i.new Dog();
        d.eat(); // Inherited method
        d.bark(); // Dog's own method
    }

}

