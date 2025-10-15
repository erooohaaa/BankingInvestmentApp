package accounts;

public class InvestmentAccount implements Account {
    private double balance = 0;
    @Override
    public void deposit(double amount) {
        balance += amount;
        System.out.println("Invested " + amount+ "Into Investment Account");
    }
    @Override
    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn " + amount + "From Investment Account");

        } else {
            System.out.println("Insufficient investment balance");

        }
    }

        @Override
        public double getBalance(){
            return balance;
        }
        @Override
        public String getDescription() {
            return "Investment Account";
        }
    @Override
    public void close() {
        System.out.println("Closing Investment Account. Final balance: " + balance);
        balance = 0;
    }
    @Override
    public double exchange(double amount) {
        return amount;
    }

}
