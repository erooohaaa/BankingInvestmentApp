package facade;

import accounts.*;
import factory.AccountFactory;

public class BankingFacade {

    public Account openAccountWithBenefits(User user) {
        Account acc = AccountFactory.createAccount("savings", user);
        acc = new RewardPointsDecorator(acc);
        System.out.println("Opened: " + acc.getDescription());
        return acc;
    }

    public Account investWithSafetyMode(User user) {
        Account acc = AccountFactory.createAccount("investment", user);
        acc = new TaxOptimizerDecorator(new InsuranceDecorator(acc));
        System.out.println("Opened: " + acc.getDescription());
        return acc;
    }

    public void closeAccount(Account account) {
        account.close();
        System.out.println("Account successfully closed.");
    }
}