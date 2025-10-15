package factory;
import accounts.*;

public class AccountFactory {

    public static Account createAccount(String type) {
        if (type == null) {
            throw new IllegalArgumentException("Account type cannot be null");
        }

        type = type.trim().toLowerCase();

        if (type.equals("savings")) {
            return new SavingAccount();
        } else if (type.equals("investment")) {
            return new InvestmentAccount();
        } else {
            throw new IllegalArgumentException("Unknown account type: " + type);
        }
    }
}