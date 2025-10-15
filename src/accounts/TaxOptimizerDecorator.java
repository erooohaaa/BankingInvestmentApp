package accounts;

public class TaxOptimizerDecorator extends BaseDecorator {
    public TaxOptimizerDecorator(Account account) {
        super(account);
    }

    @Override
    public void deposit(double amount) {
        double optimized = amount * 1.02;
        super.deposit(optimized);
        System.out.println("Tax optimization applied (+2%).");
    }

    @Override
    public String getDescription() {
        return wrappee.getDescription() + " + Tax Optimization";
    }
}
