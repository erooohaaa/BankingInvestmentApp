package demo;

import accounts.Account;
import accounts.User;
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
            User user = acc.getUser();
            if (user != null && user.getId().equals(searchId)) {
                System.out.println("ACTIVE ACCOUNT FOUND:");
                System.out.println(" - Description: " + acc.getDescription());
                System.out.println(" - Current Balance: " + acc.getBalance());
                System.out.println(" - User: " + user.toString());
                foundInMemory = true;
            }
        }

        boolean foundInFile = searchInFile(searchId);

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

    public void displayAllActiveAccounts() {
        System.out.println("\n=== ALL ACTIVE ACCOUNTS ===");
        if (activeAccounts.isEmpty()) {
            System.out.println("No active accounts found.");
            return;
        }

        for (int i = 0; i < activeAccounts.size(); i++) {
            Account acc = activeAccounts.get(i);
            User user = acc.getUser();

            System.out.println((i + 1) + ". " + acc.getDescription());
            System.out.println("   Balance: " + acc.getBalance());
            if (user != null) {
                System.out.println("   User: " + user.toString());
            }
            System.out.println();
        }
    }
}