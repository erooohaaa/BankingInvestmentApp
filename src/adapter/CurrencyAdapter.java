package adapter;

public class CurrencyAdapter {
    public double convert(String from, String to, double amount) {
        double rate = CurrencyRates.getRate(from, to);
        return amount * rate;
    }
}