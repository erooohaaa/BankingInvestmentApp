package demo;

import accounts.Account;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminPanel {
    private List<Account> activeAccounts;

    public AdminPanel() {
        this.activeAccounts = new ArrayList<>();
    }

    public void addActiveAccount(Account account) {
        this.activeAccounts.add(account);
    }

    public void searchAccountByID(String searchId) {
        System.out.println("\n=== SEARCH RESULTS FOR ID: " + searchId + " ===");

        boolean foundInMemory = false;
        for (Account acc : activeAccounts) {
            try {
                java.lang.reflect.Method getUserMethod = acc.getClass().getMethod("getUser");
                Object userObj = getUserMethod.invoke(acc);

                if (userObj != null) {
                    java.lang.reflect.Method getIdMethod = userObj.getClass().getMethod("getId");
                    String userId = (String) getIdMethod.invoke(userObj);

                    if (userId.equals(searchId)) {
                        System.out.println("ACTIVE ACCOUNT FOUND:");
                        System.out.println(" - Description: " + acc.getDescription());
                        System.out.println(" - Current Balance: " + acc.getBalance());
                        System.out.println(" - User: " + userObj.toString());


                        try {
                            java.lang.reflect.Method getHistoryMethod = acc.getClass().getMethod("getTransactionHistory");
                            List<?> history = (List<?>) getHistoryMethod.invoke(acc);
                            if (!history.isEmpty()) {
                                System.out.println(" - Recent Transactions:");
                                for (int i = 0; i < Math.min(history.size(), 3); i++) {
                                    System.out.println("   * " + history.get(i));
                                }
                            }
                        } catch (Exception e) {

                        }
                        foundInMemory = true;
                    }
                }
            } catch (Exception e) {

            }
        }


        boolean foundInFile = searchInFile(searchId);


        searchTransactionsInFile(searchId);


        displayTransferHistoryForAccount(searchId);

        if (!foundInMemory && !foundInFile) {
            System.out.println("No accounts found with ID: " + searchId);
        }
    }

    private boolean searchInFile(String searchId) {
        try {
            File file = new File("users.txt");
            if (!file.exists()) {
                System.out.println("No user database file found.");
                return false;
            }

            Scanner fileScanner = new Scanner(file);
            boolean found = false;

            System.out.println("\nACCOUNTS FROM DATABASE:");
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split("\\|");

                if (parts.length >= 4 && parts[2].equals(searchId)) {
                    System.out.println(" - User: " + parts[0] + " " + parts[1]);
                    System.out.println("   ID: " + parts[2]);
                    System.out.println("   Email: " + parts[3]);
                    System.out.println("   Account Type: " + (parts.length > 4 ? parts[4] : "Unknown"));
                    System.out.println("   Created: " + (parts.length > 5 ? parts[5] : "Unknown"));
                    found = true;
                }
            }
            fileScanner.close();
            return found;

        } catch (FileNotFoundException e) {
            System.out.println("User database file not found: " + e.getMessage());
            return false;
        }
    }

    private void searchTransactionsInFile(String searchId) {
        try {
            File file = new File("transactions.txt");
            if (!file.exists()) {
                return;
            }

            Scanner fileScanner = new Scanner(file);
            List<String> transactions = new ArrayList<>();

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split("\\|");

                if (parts.length >= 2 && parts[0].equals(searchId)) {
                    transactions.add(line);
                }
            }
            fileScanner.close();

            if (!transactions.isEmpty()) {
                System.out.println("\nTRANSACTION HISTORY FROM FILE:");
                for (int i = Math.max(0, transactions.size() - 5); i < transactions.size(); i++) {
                    String[] parts = transactions.get(i).split("\\|");
                    if (parts.length >= 7) {
                        System.out.printf(" - %s: %s $%.2f (Balance: $%.2f) - %s%n",
                                parts[2], parts[3], Double.parseDouble(parts[4]),
                                Double.parseDouble(parts[5]), parts[6]);
                    }
                }
            }

        } catch (FileNotFoundException e) {

        }
    }

    private void displayTransferHistoryForAccount(String accountId) {
        try {
            File file = new File("transfers.txt");
            if (!file.exists()) {
                return;
            }

            Scanner fileScanner = new Scanner(file);
            System.out.println("\nTRANSFER HISTORY:");
            boolean found = false;

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split("\\|");

                if (parts.length >= 6 && (parts[0].equals(accountId) || parts[1].equals(accountId))) {
                    found = true;
                    if (parts[0].equals(accountId)) {
                        System.out.printf(" - Sent $%.2f to %s at %s%n",
                                Double.parseDouble(parts[2]), parts[1], parts[5]);
                    } else {
                        System.out.printf(" - Received $%.2f from %s at %s%n",
                                Double.parseDouble(parts[2]), parts[0], parts[5]);
                    }
                }
            }
            fileScanner.close();

            if (!found) {
                System.out.println(" - No transfers found");
            }

        } catch (FileNotFoundException e) {

        }
    }

    public void displayAllActiveAccounts() {
        System.out.println("\n=== ALL ACTIVE ACCOUNTS ===");
        if (activeAccounts.isEmpty()) {
            System.out.println("No active accounts found.");
            return;
        }

        for (int i = 0; i < activeAccounts.size(); i++) {
            Account acc = activeAccounts.get(i);
            try {
                java.lang.reflect.Method getUserMethod = acc.getClass().getMethod("getUser");
                Object userObj = getUserMethod.invoke(acc);

                System.out.println((i + 1) + ". " + acc.getDescription());
                System.out.println("   Balance: " + acc.getBalance());
                if (userObj != null) {
                    System.out.println("   User: " + userObj.toString());
                }
                System.out.println();
            } catch (Exception e) {
                System.out.println((i + 1) + ". " + acc.getDescription() + " (User info unavailable)");
                System.out.println("   Balance: " + acc.getBalance());
                System.out.println();
            }
        }
    }
}