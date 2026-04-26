public class OOPRecap {
    public static void main(String[] args) {

        SimpleBankAccount account = new SimpleBankAccount();
        account.deposit(500);
        account.withdraw(100);
        System.out.println("Balance: " + account.getBalance());


    }
}

class SimpleBankAccount {
    private int balance;


    void deposit(int amount){
        balance += amount;
    }
    void withdraw(int amount){
        balance -= amount;
    }

    public int getBalance(){
        return balance;
    }

    public int setBalance(int amount){
        balance -= amount;
        return balance;
    }
}
