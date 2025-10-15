package accounts;

import adapter.CurrencyAdapter;

public class SavingAccount implements Account {
    private double balance = 0;

    @Override
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive");
            return;
        }
        balance += amount;
        System.out.println("Deposited " + amount + " Into Saving Account. New balance: " + balance);
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive");
            return;
        }
        if (amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn " + amount + " From Saving Account. New balance: " + balance);
        } else {
            System.out.println("Insufficient Funds");
        }
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public String getDescription() {
        return "Saving Account";
    }

    @Override
    public void close() {
        System.out.println("Closing Saving Account. Final balance: " + balance);
        balance = 0;
    }

    @Override
    public double exchange(double amount, String fromCurrency, String toCurrency) {
        CurrencyAdapter adapter = new CurrencyAdapter();
        double converted = adapter.convert(fromCurrency, toCurrency, amount);
        System.out.printf("Exchanged %.2f %s to %.2f %s%n", amount, fromCurrency, converted, toCurrency);
        return converted;
    }
}