package accounts;

import adapter.CurrencyAdapter;

public class InvestmentAccount implements Account {
    private double balance = 0;
    private User user;

    public InvestmentAccount(User user) {
        this.user = user;
        user.saveToFile("InvestmentAccount");
    }

    @Override
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive");
            return;
        }
        balance += amount;
        System.out.println("Invested " + amount + " Into Investment Account. New balance: " + balance);
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive");
            return;
        }
        if (amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn " + amount + " From Investment Account. New balance: " + balance);
        } else {
            System.out.println("Insufficient investment balance");
        }
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public String getDescription() {
        return "Investment Account" + (user != null ? " (" + user.getFirstName() + " " + user.getLastName() + ")" : "");
    }

    @Override
    public void close() {
        System.out.println("Closing Investment Account. Final balance: " + balance);
        balance = 0;
    }

    @Override
    public double exchange(double amount, String fromCurrency, String toCurrency) {
        CurrencyAdapter adapter = new CurrencyAdapter();
        double converted = adapter.convert(fromCurrency, toCurrency, amount);
        System.out.printf("Exchanged %.2f %s to %.2f %s%n", amount, fromCurrency, converted, toCurrency);
        return converted;
    }

    @Override
    public User getUser() {
        return user;
    }
}