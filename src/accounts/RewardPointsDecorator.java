package accounts;

public class RewardPointsDecorator extends BaseDecorator {
    private int rewardPoints = 0;

    public RewardPointsDecorator(Account account) {
        super(account);
    }

    @Override
    public void deposit(double amount) {
        super.deposit(amount);
        rewardPoints += (int) (amount / 10);
        System.out.println("Added " + (int)(amount / 10) + " reward points.");
    }

    @Override
    public String getDescription() {
        return wrappee.getDescription() + " + Reward Points System";
    }
}