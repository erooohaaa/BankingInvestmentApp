package accounts;

import java.time.LocalDateTime;

public class Transaction {
    private String type; // DEPOSIT, WITHDRAW, EXCHANGE
    private double amount;
    private double balanceAfter;
    private LocalDateTime timestamp;
    private String description;

    public Transaction(String type, double amount, double balanceAfter, String description) {
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.timestamp = LocalDateTime.now();
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s: $%.2f | Balance: $%.2f | %s",
                timestamp, type, amount, balanceAfter, description);
    }

    public String toFileString() {
        return String.format("%s|%.2f|%.2f|%s|%s",
                type, amount, balanceAfter, timestamp, description);
    }

    public String getType() { return type; }
    public double getAmount() { return amount; }
    public LocalDateTime getTimestamp() { return timestamp; }
}