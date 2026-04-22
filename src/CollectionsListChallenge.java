import java.util.ArrayList;
import java.util.Collections;

public class CollectionsListChallenge {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<String>();

        list.add("Abdullah");
        list.add("Sara");
        list.add("Rashid");

        System.out.println(list); // Print all names
        list.remove("Abdullah"); // remove the name
        System.out.println(list); // print the new update
        System.out.println(list.size());
        System.out.println(list.contains("Rashid"));
        System.out.println(list.contains("Abdullah"));
        Collections.sort(list);
        System.out.println(list+ " The list after sorting"); // sorting the names in order  - Alphabetical sort
        System.out.println(list.get(0)); // get the first name in the list
        list.sort(Collections.reverseOrder());//Reverse Order
        System.out.println(list+ " The list after sorting");



    }
}
