public class Encapsulation {
    private double balance;

    public void deposit(double amount){
        balance += amount;
    }
    public double getBalance(){
        return balance;
    }



    public static void main(String[] args) {
        Encapsulation e = new Encapsulation();
        e.deposit(500);
        System.out.println(e.getBalance());

    }
}
