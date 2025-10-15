package builder;

public class PortfolioBuilder {
    private String owner;
    private double stocks;
    private double bonds;
    private double crypto;

    public PortfolioBuilder setOwner(String owner) {
        this.owner = owner;
        return this;
    }

    public PortfolioBuilder setStocks(double stocks) {
        this.stocks = stocks;
        return this;
    }

    public PortfolioBuilder setBonds(double bonds) {
        this.bonds = bonds;
        return this;
    }

    public PortfolioBuilder setCrypto(double crypto) {
        this.crypto = crypto;
        return this;
    }

    public Portfolio build() {
        return new Portfolio(owner, stocks, bonds, crypto);
    }
}
