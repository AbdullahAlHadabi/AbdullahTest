/*
import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class Testfile {
    public static void main(String[] args) {

        HashMap<Integer,Car> map = new HashMap<>();

        try{
            File file = new File("src/cars.txt");
            Scanner sc = new Scanner(file);

            int id=1;
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                if (line.isEmpty()) continue;

                String[] split = line.split(",");

                String name = split[0];
                int year = Integer.parseInt(split[1]);


                Car car = new Car(name,year);
                map.put(id,car);
                id++;
            }
            sc.close();
        } catch (Exception e) {
            System.out.println("Error reading file");

        }
        for (Integer key : map.keySet()) {
            System.out.print("ID: " + key + " -> ");
            map.get(key).display();
        }

    }
}
/*
class {
    String brand;
    int year;

    public Car(String brand, int year){
        this.brand = brand;
        this.year = year;
    }
    public void display(){
        System.out.println("brand:"+brand);
        System.out.println("year:"+year);
    }
}
*/