public class OOPRecap {
    public static void main(String[] args) {

        BankAccount account = new BankAccount();
        account.deposit(500);
        account.withdraw(100);
        System.out.println("Balance: " + account.getBalance());


    }
}

class BankAccount {
    private int blance;


    void deposit(int amount){
        blance += amount;
    }
    void withdraw(int amount){
        blance -= amount;
    }

    public int getBalance(){
        return blance;
    }

    public int setBalance(int amount){
        blance -= amount;
        return blance;
    }
}
