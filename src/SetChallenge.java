import java.util.*;

public class SetChallenge {
    public static void main(String[] args) {
        Set<String> Country = new HashSet<>();
        Country.add("Australia");
        Country.add("India");
        Country.add("Russia");
        Country.add("Ukraine");
        Country.add("Ukraine");
        Country.add("Brazil");
        Country.add("Oman");

        System.out.println(Country); // here After printing we can see that Set Avoid duplicate

        // now we change to list for sorting

        List<String> sortedCountries = new ArrayList<>(Country);
        Collections.sort(sortedCountries);
        System.out.println("Here we sort them : "+ sortedCountries);


    }
}
