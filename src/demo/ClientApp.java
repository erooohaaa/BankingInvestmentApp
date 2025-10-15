package demo;

import adapter.CurrencyAdapter;
import builder.Portfolio;
import builder.PortfolioBuilder;
import facade.BankingFacade;
import accounts.Account;

import java.util.Scanner;

public class ClientApp {
    public void run() {
        BankingFacade facade = new BankingFacade();
        CurrencyAdapter adapter = new CurrencyAdapter();

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n= Banking & Investment Menu =");
            System.out.println("1. Open Account with Benefits");
            System.out.println("2. Open Safe Investment Account");
            System.out.println("3. Build Portfolio");
            System.out.println("4. Convert Currency");
            System.out.println("5. Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> {
                    Account a = facade.openAccountWithBenefits();
                    a.deposit(1000);
                    System.out.println("Balance: " + a.getBalance());
                }
                case 2 -> {
                    Account a = facade.investWithSafetyMode();
                    a.deposit(2000);
                    System.out.println("Balance: " + a.getBalance());
                }
                case 3 -> {
                    Portfolio p = new PortfolioBuilder()
                            .setOwner("Yerkanat Marat")
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
                    double converted = adapter.convert(from, to, amt);
                    System.out.printf("%.2f %s = %.2f %s%n", amt, from, converted, to);
                }

                case 5 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }
}