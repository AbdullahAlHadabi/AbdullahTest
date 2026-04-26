public class OOPClass {
    public static void main(String[] args) {
       AccountDetails account = new AccountDetails(123, "Abdullah", 500.123);

         System.out.println("Balance: " + account.getBalance());
         account.displayAccountInfo();

    }
}
class AccountDetails{
    private int id;
    private String name;
    private double balance;

    public AccountDetails(int id, String name, double balance){
        this.id = id;
        this.name = name;
        this.balance = balance;

    }

    public int getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public double getBalance(){
        return this.balance;
    }

    void displayAccountInfo(){
        System.out.println("Customer name: " + name + ", Balance: " + balance + ", ID: " + id );
    }
}
