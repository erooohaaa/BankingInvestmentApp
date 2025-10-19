package services;

import accounts.Account;
import demo.AdminPanel;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class TransferService {
    private AdminPanel adminPanel;

    public TransferService(AdminPanel adminPanel) {
        this.adminPanel = adminPanel;
    }

    public boolean transfer(Account fromAccount, String toAccountId, double amount) {
        if (amount <= 0) {
            System.out.println("Transfer amount must be positive");
            return false;
        }


        if (fromAccount.getBalance() < amount) {
            System.out.println("Insufficient funds for transfer");
            return false;
        }


        Account toAccount = findAccountById(toAccountId);
        if (toAccount == null) {
            System.out.println("Recipient account not found with ID: " + toAccountId);
            return false;
        }


        fromAccount.withdraw(amount);
        toAccount.deposit(amount);


        saveTransferToFile(fromAccount, toAccount, amount);

        System.out.printf("Transfer successful: $%.2f sent to account %s%n", amount, toAccountId);
        return true;
    }

    private Account findAccountById(String accountId) {
        try {

            java.lang.reflect.Field activeAccountsField = adminPanel.getClass().getDeclaredField("activeAccounts");
            activeAccountsField.setAccessible(true);

            @SuppressWarnings("unchecked")
            List<Account> activeAccounts = (List<Account>) activeAccountsField.get(adminPanel);

            for (Account acc : activeAccounts) {
                try {
                    java.lang.reflect.Method getUserMethod = acc.getClass().getMethod("getUser");
                    Object userObj = getUserMethod.invoke(acc);

                    if (userObj != null) {
                        java.lang.reflect.Method getIdMethod = userObj.getClass().getMethod("getId");
                        String userId = (String) getIdMethod.invoke(userObj);

                        if (userId.equals(accountId)) {
                            return acc;
                        }
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        } catch (Exception e) {
            System.out.println("Error searching for account: " + e.getMessage());
        }
        return null;
    }

    private void saveTransferToFile(Account fromAccount, Account toAccount, double amount) {
        try {
            File file = new File("transfers.txt");
            if (!file.exists()) {
                file.createNewFile();
            }

            String fromUserId = getUserId(fromAccount);
            String toUserId = getUserId(toAccount);

            FileWriter writer = new FileWriter("transfers.txt", true);
            writer.write(String.format("%s|%s|%.2f|%s|%s|%s%n",
                    fromUserId, toUserId, amount,
                    fromAccount.getDescription(), toAccount.getDescription(),
                    LocalDateTime.now()));
            writer.close();

        } catch (IOException e) {
            System.out.println("Error saving transfer record: " + e.getMessage());
        }
    }

    private String getUserId(Account account) {
        try {
            java.lang.reflect.Method getUserMethod = account.getClass().getMethod("getUser");
            Object userObj = getUserMethod.invoke(account);

            if (userObj != null) {
                java.lang.reflect.Method getIdMethod = userObj.getClass().getMethod("getId");
                return (String) getIdMethod.invoke(userObj);
            }
        } catch (Exception e) {

        }
        return account.getDescription();
    }

    public void displayTransferHistory(String accountId) {
        try {
            File file = new File("transfers.txt");
            if (!file.exists()) {
                System.out.println("No transfer history found");
                return;
            }

            java.util.Scanner scanner = new java.util.Scanner(file);
            System.out.println("\n=== TRANSFER HISTORY FOR ACCOUNT: " + accountId + " ===");
            boolean found = false;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\\|");

                if (parts.length >= 6 && (parts[0].equals(accountId) || parts[1].equals(accountId))) {
                    found = true;
                    if (parts[0].equals(accountId)) {

                        System.out.printf("OUT: $%.2f to %s (%s) at %s%n",
                                Double.parseDouble(parts[2]), parts[1], parts[4], parts[5]);
                    } else {

                        System.out.printf("IN: $%.2f from %s (%s) at %s%n",
                                Double.parseDouble(parts[2]), parts[0], parts[3], parts[5]);
                    }
                }
            }
            scanner.close();

            if (!found) {
                System.out.println("No transfers found for this account");
            }

        } catch (IOException e) {
            System.out.println("Error reading transfer history: " + e.getMessage());
        }
    }
}