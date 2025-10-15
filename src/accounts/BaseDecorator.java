package accounts;

public abstract class BaseDecorator implements Account {
    protected Account wrappee;

    public BaseDecorator(Account account) {
        this.wrappee = account;
    }

    @Override
    public void deposit(double amount) {
        wrappee.deposit(amount);
    }

    @Override
    public void withdraw(double amount) {
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
    public double exchange(double amount) {
        return wrappee.exchange(amount);
    }
}
