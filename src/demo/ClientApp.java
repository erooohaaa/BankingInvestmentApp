package demo;

import adapter.CurrencyAdapter;
import builder.Portfolio;
import builder.PortfolioBuilder;
import facade.BankingFacade;
import accounts.Account;
import accounts.User;

import java.util.Scanner;
import java.util.HashSet;
import java.util.Set;

public class ClientApp {
    private AdminPanel adminPanel;
    private Set<String> usedIds = new HashSet<>(); // Храним использованные ID

    public void run() {
        BankingFacade facade = new BankingFacade();
        adminPanel = new AdminPanel();
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
            System.out.println("6. Deposit Money");
            System.out.println("7. Withdraw Money");
            System.out.println("8. Admin - Search Account by ID");
            System.out.println("9. Admin - Show All Active Accounts");
            System.out.println("0. Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> {
                    User user = createUser(sc);
                    if (user != null) {
                        currentAccount = facade.openAccountWithBenefits(user);
                        adminPanel.addActiveAccount(currentAccount);
                        usedIds.add(user.getId()); // Добавляем ID в использованные
                        System.out.println("Account opened successfully!");
                    }
                }
                case 2 -> {
                    User user = createUser(sc);
                    if (user != null) {
                        currentAccount = facade.investWithSafetyMode(user);
                        adminPanel.addActiveAccount(currentAccount);
                        usedIds.add(user.getId()); // Добавляем ID в использованные
                        System.out.println("Account opened successfully!");
                    }
                }
                case 3 -> {
                    System.out.print("Enter portfolio owner name: ");
                    String owner = sc.nextLine();

                    System.out.print("Enter stocks amount: ");
                    double stocks = sc.nextDouble();

                    System.out.print("Enter bonds amount: ");
                    double bonds = sc.nextDouble();

                    System.out.print("Enter crypto amount: ");
                    double crypto = sc.nextDouble();

                    sc.nextLine();

                    Portfolio p = new PortfolioBuilder()
                            .setOwner(owner)
                            .setStocks(stocks)
                            .setBonds(bonds)
                            .setCrypto(crypto)
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
                        System.out.print("Enter deposit amount: ");
                        double depositAmount = sc.nextDouble();
                        sc.nextLine();

                        currentAccount.deposit(depositAmount);
                        System.out.println("Balance: " + currentAccount.getBalance());
                    } else {
                        System.out.println("No account opened!");
                    }
                }
                case 7 -> {
                    if (currentAccount != null) {
                        System.out.print("Enter withdrawal amount: ");
                        double withdrawAmount = sc.nextDouble();
                        sc.nextLine();

                        currentAccount.withdraw(withdrawAmount);
                        System.out.println("Balance: " + currentAccount.getBalance());
                    } else {
                        System.out.println("No account opened!");
                    }
                }
                case 8 -> {
                    System.out.print("Enter user ID to search: ");
                    String searchId = sc.nextLine();
                    adminPanel.searchAccountByID(searchId);
                }
                case 9 -> {
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

    private User createUser(Scanner sc) {
        System.out.println("\n=== Enter User Details ===");
        System.out.print("Enter first name: ");
        String firstName = sc.nextLine();
        System.out.print("Enter last name: ");
        String lastName = sc.nextLine();

        // Генерация уникального ID
        String id = generateUniqueId();
        System.out.println("Your unique ID: " + id);

        String email;
        while (true) {
            System.out.print("Enter email: ");
            email = sc.nextLine();

            if (isValidEmail(email)) {
                break;
            } else {
                System.out.println("Invalid email format! Please enter a valid email (e.g., user@example.com)");
            }
        }

        try {
            User user = new User(firstName, lastName, id, email);
            return user;
        } catch (IllegalArgumentException e) {
            System.out.println("Error creating user: " + e.getMessage());
            return null;
        }
    }


    private String generateUniqueId() {
        String id;
        do {

            id = String.valueOf((int)(Math.random() * 900000) + 100000);
        } while (usedIds.contains(id));
        return id;
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
}