package accounts;

public abstract class BaseDecorator implements Account {
    protected Account wrappee;

    public BaseDecorator(Account account) {
        this.wrappee = account;
    }

    @Override
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive");
            return;
        }
        wrappee.deposit(amount);
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive");
            return;
        }
        wrappee.withdraw(amount);
    }

    @Override
    public double getBalance() {
        return wrappee.getBalance();
    }

    @Override
    public String getDescription() {
        return wrappee.getDescription();
    }

    @Override
    public void close() {
        wrappee.close();
    }

    @Override
    public double exchange(double amount, String fromCurrency, String toCurrency) {
        return wrappee.exchange(amount, fromCurrency, toCurrency);
    }

    @Override
    public User getUser() {
        return wrappee.getUser();
    }
}