package adapter;

public class CurrencyRates {

    public static double getRate(String from, String to) {
        if (from.equals("USD") && to.equals("KZT")) return 538.0;
        if (from.equals("EUR") && to.equals("KZT")) return 625.0;
        if (from.equals("KZT") && to.equals("USD")) return 1.0 / 538.0;
        if (from.equals("KZT") && to.equals("EUR")) return 1.0 / 625.0;
        if (from.equals("USD") && to.equals("EUR")) return 538.0 / 625.0;
        if (from.equals("EUR") && to.equals("USD")) return 625.0 / 538.0;
        return 1.0;
    }
}