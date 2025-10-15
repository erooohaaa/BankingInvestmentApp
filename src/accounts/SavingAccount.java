package accounts;

public class SavingAccount implements Account {
    public double balance = 0;
    @Override
    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited " + amount+ "Into Saving Account");

    }
    @Override
    public void withdraw(double amount) {
        if(amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn " + amount+ "From Saving Account");
        }


    else{
        System.out.println("Insufficient Funds");
    }
    }
    public double getBalance() {
        return balance;
    }
    @Override
    public String getDescription() {
        return "Saving Account";
    }
    public  void close(){
        System.out.println("Closing Saving Account. Final balance: " + balance);
        balance = 0;
    }
    @Override
    public double exchange(double amount) {
        return amount;
    }
}
