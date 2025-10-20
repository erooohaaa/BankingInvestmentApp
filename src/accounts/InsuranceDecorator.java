package accounts;

public class InsuranceDecorator extends BaseDecorator {
    public InsuranceDecorator(Account account) {
        super(account);
    }

    @Override
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive");
            return;
        }

        double insuranceFee = amount * 0.20;
        double netAmount = amount - insuranceFee;

        System.out.printf("Insurance fee (20%%): $%.2f deducted%n", insuranceFee);
        System.out.printf("Net deposit after insurance: $%.2f%n", netAmount);

        wrappee.deposit(netAmount);
    }

    @Override
    public String getDescription() {
        return wrappee.getDescription() + " + Insurance Protection (20% fee)";
    }
}