package accounts;

import adapter.CurrencyAdapter;
import java.util.ArrayList;
import java.util.List;

public class InvestmentAccount implements Account {
    private double balance = 0;
    private User user;
    private List<Transaction> transactionHistory;

    public InvestmentAccount(User user) {
        this.user = user;
        this.transactionHistory = new ArrayList<>();
        user.saveToFile("InvestmentAccount");
        addTransaction("ACCOUNT_CREATION", 0, "Account opened");
    }

    @Override
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive");
            return;
        }
        balance += amount;
        addTransaction("DEPOSIT", amount, "Investment deposit");
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
            addTransaction("WITHDRAW", amount, "Investment withdrawal");

            if (!Thread.currentThread().getStackTrace()[2].getMethodName().equals("transfer")) {
                System.out.println("Withdrawn " + amount + " From Investment Account. New balance: " + balance);
            }
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
        addTransaction("ACCOUNT_CLOSED", 0, "Account closed with balance: " + balance);
        balance = 0;
    }

    @Override
    public double exchange(double amount, String fromCurrency, String toCurrency) {
        CurrencyAdapter adapter = new CurrencyAdapter();
        double converted = adapter.convert(fromCurrency, toCurrency, amount);
        addTransaction("EXCHANGE", amount, "Currency exchange: " + fromCurrency + " to " + toCurrency);
        System.out.printf("Exchanged %.2f %s to %.2f %s%n", amount, fromCurrency, converted, toCurrency);
        return converted;
    }

    public void displayTransactionHistory() {
        System.out.println("\n=== TRANSACTION HISTORY ===");
        System.out.println("Account: " + getDescription());
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            for (Transaction transaction : transactionHistory) {
                System.out.println("  " + transaction);
            }
        }
    }

    private void addTransaction(String type, double amount, String description) {
        Transaction transaction = new Transaction(type, amount, balance, description);
        transactionHistory.add(transaction);
        saveTransactionToFile(transaction);
    }

    private void saveTransactionToFile(Transaction transaction) {
        try {
            java.io.File file = new java.io.File("transactions.txt");
            if (!file.exists()) {
                file.createNewFile();
            }

            java.io.FileWriter writer = new java.io.FileWriter("transactions.txt", true);
            writer.write(user.getId() + "|" + getDescription() + "|" + transaction.toFileString() + "\n");
            writer.close();
        } catch (java.io.IOException e) {
            System.out.println("Error saving transaction: " + e.getMessage());
        }
    }

    public User getUser() {
        return user;
    }

    public List<Transaction> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }
}