package accounts;

public class InsuranceDecorator extends BaseDecorator {
    public InsuranceDecorator(Account account) {
        super(account);
    }

    @Override
    public String getDescription() {
        return wrappee.getDescription() + " + Insurance Protection";
    }
}
