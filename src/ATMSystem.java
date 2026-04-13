

    class Account {
        private double balance;

        public Account(double balance) {
            this.balance = balance;
        }


        public void Deposit(double amount) {
            if (amount < 0) {
                System.out.println("No balance !!");
            } else {
                balance += amount;
                System.out.println("Deposited " +balance);
            }
        }

        public void Withdraw(double amount) {
            if (amount < balance) {
                balance -= amount;
                System.out.println("Withdrawn " + amount);
            } else {
                System.out.println("No balance !!");
            }
        }

        public double getBalance() {
            return balance;
        }
    }


    public class ATMSystem {
        public static void main(String[] args) {
            Account account = new Account(1000);
            account.Deposit(500);
            System.out.println("Current Balance: " + account.getBalance());
            account.Withdraw(200);
            System.out.println("Current Balance: " + account.getBalance());
            account.Withdraw(1500);
        }
    }





