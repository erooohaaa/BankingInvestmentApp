package facade;

import accounts.*;
import factory.AccountFactory;

public class BankingFacade {


    public Account openAccountWithBenefits() {
        Account acc = AccountFactory.createAccount("savings");
        acc = new RewardPointsDecorator(new InsuranceDecorator(acc));
        System.out.println("Opened: " + acc.getDescription());
        return acc;
    }


    public Account investWithSafetyMode() {
        Account acc = AccountFactory.createAccount("investment");
        acc = new TaxOptimizerDecorator(new InsuranceDecorator(acc));
        System.out.println("Opened: " + acc.getDescription());
        return acc;
    }


    public void closeAccount(Account account) {
        account.close();
        System.out.println("Account successfully closed.");
    }
}
