package builder;

public class Portfolio {
    private String owner;
    private double stocks;
    private double bonds;
    private double crypto;

    public Portfolio(String owner, double stocks, double bonds, double crypto) {
        this.owner = owner;
        this.stocks = stocks;
        this.bonds = bonds;
        this.crypto = crypto;
    }

    @Override
    public String toString() {
        return "Portfolio of " + owner + ": " +
                "Stocks=" + stocks + ", Bonds=" + bonds + ", Crypto=" + crypto;
    }
}
