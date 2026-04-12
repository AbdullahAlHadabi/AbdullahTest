public class AbdullahTest {

        String name;
        int price;


        AbdullahTest(String n, int p) {
            name=n;
            price=p;
        }


    public static void main(String[] args) {
        AbdullahTest A = new AbdullahTest("BMW",100);
        System.out.println(A.name);
        System.out.println(A.price);


    }
}