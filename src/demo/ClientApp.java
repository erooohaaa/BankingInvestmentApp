package demo;

import adapter.CurrencyAdapter;
import builder.Portfolio;
import builder.PortfolioBuilder;
import facade.BankingFacade;
import accounts.Account;
import accounts.User;
import services.TransferService;

import java.util.Scanner;

public class ClientApp {
    private AdminPanel adminPanel;
    private TransferService transferService;

    public void run() {
        BankingFacade facade = new BankingFacade();
        adminPanel = new AdminPanel();
        transferService = new TransferService(adminPanel);
        Account currentAccount = null;

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("           BANKING & INVESTMENT SYSTEM");
            System.out.println("=".repeat(50));
            System.out.println("1. Open Account with Benefits");
            System.out.println("2. Open Safe Investment Account");
            System.out.println("3. Build Portfolio");
            System.out.println("4. Currency Exchange");
            System.out.println("5. Show Current Account Info");
            System.out.println("6. Show Transaction History");
            System.out.println("7. Transfer Money to Another Account");
            System.out.println("8. Show Transfer History");
            System.out.println("9. Admin - Search Account by ID");
            System.out.println("10. Admin - Show All Active Accounts");
            System.out.println("0. Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> {
                    User user = createUser(sc);
                    currentAccount = facade.openAccountWithBenefits(user);
                    adminPanel.addActiveAccount(currentAccount);
                    currentAccount.deposit(1000);
                    System.out.println("Balance: " + currentAccount.getBalance());
                }
                case 2 -> {
                    User user = createUser(sc);
                    currentAccount = facade.investWithSafetyMode(user);
                    adminPanel.addActiveAccount(currentAccount);
                    currentAccount.deposit(2000);
                    System.out.println("Balance: " + currentAccount.getBalance());
                }
                case 3 -> {
                    System.out.print("Enter portfolio owner name: ");
                    String owner = sc.nextLine();
                    Portfolio p = new PortfolioBuilder()
                            .setOwner(owner)
                            .setStocks(5000)
                            .setBonds(2000)
                            .setCrypto(1000)
                            .build();
                    System.out.println(p);
                }
                case 4 -> {
                    System.out.print("From currency (e.g., USD): ");
                    String from = sc.nextLine().trim().toUpperCase();
                    System.out.print("To currency (e.g., KZT): ");
                    String to = sc.nextLine().trim().toUpperCase();
                    System.out.print("Amount: ");
                    double amt = sc.nextDouble();

                    if (currentAccount != null) {
                        double converted = currentAccount.exchange(amt, from, to);
                        System.out.printf("Via Account Exchange: %.2f %s = %.2f %s%n",
                                amt, from, converted, to);
                    } else {
                        CurrencyAdapter adapter = new CurrencyAdapter();
                        double converted = adapter.convert(from, to, amt);
                        System.out.printf("Direct Conversion: %.2f %s = %.2f %s%n",
                                amt, from, converted, to);
                    }
                }
                case 5 -> {
                    if (currentAccount != null) {
                        System.out.println("Account: " + currentAccount.getDescription());
                        System.out.println("Balance: " + currentAccount.getBalance());
                    } else {
                        System.out.println("No account opened!");
                    }
                }
                case 6 -> {
                    if (currentAccount != null) {
                        try {
                            java.lang.reflect.Method displayHistory =
                                    currentAccount.getClass().getMethod("displayTransactionHistory");
                            displayHistory.invoke(currentAccount);
                        } catch (Exception e) {
                            System.out.println("Transaction history not available for this account.");
                        }
                    } else {
                        System.out.println("No account opened!");
                    }
                }
                case 7 -> {
                    if (currentAccount != null) {
                        System.out.print("Enter recipient account ID: ");
                        String recipientId = sc.nextLine();
                        System.out.print("Enter transfer amount: ");
                        double transferAmount = sc.nextDouble();
                        sc.nextLine(); // consume newline

                        transferService.transfer(currentAccount, recipientId, transferAmount);
                    } else {
                        System.out.println("No account opened! Please open an account first.");
                    }
                }
                case 8 -> {
                    if (currentAccount != null) {
                        String currentUserId = getUserId(currentAccount);
                        if (currentUserId != null) {
                            transferService.displayTransferHistory(currentUserId);
                        } else {
                            System.out.println("Cannot identify current account ID");
                        }
                    } else {
                        System.out.println("No account opened!");
                    }
                }
                case 9 -> {
                    System.out.print("Enter user ID to search: ");
                    String searchId = sc.nextLine();
                    adminPanel.searchAccountByID(searchId);
                }
                case 10 -> {
                    adminPanel.displayAllActiveAccounts();
                }
                case 0 -> {
                    System.out.println("Thank you for using our Banking System!");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
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
            System.out.println("Error getting user ID: " + e.getMessage());
        }
        return null;
    }

    private User createUser(Scanner sc) {
        System.out.println("\n=== Enter User Details ===");
        System.out.print("Enter first name: ");
        String firstName = sc.nextLine();
        System.out.print("Enter last name: ");
        String lastName = sc.nextLine();
        System.out.print("Enter ID: ");
        String id = sc.nextLine();
        System.out.print("Enter email: ");
        String email = sc.nextLine();

        return new User(firstName, lastName, id, email);
    }
}