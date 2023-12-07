import java.util.HashMap;
import java.util.Map;

/**
 * Represents a trader with a portfolio of cryptocurrencies and available funds.
 * This class allows for buying and selling cryptocurrencies, managing funds,
 * and tracking holdings in the portfolio.
 */
public class Trader {
    private double funds;
    private Map<String, Double> portfolio;

    /**
     * Constructor for creating a new Trader instance.
     *
     * @param startingFunds The initial amount of money the trader starts with.
     */
    public Trader(double startingFunds) {
        this.funds = startingFunds;
        this.portfolio = new HashMap<>(); // Initialize portfolio to track cryptocurrency holdings.
    }

    /**
     * Checks if the trader can afford to buy a specified amount of a cryptocurrency.
     *
     * @param currencyName The name of the cryptocurrency.
     * @param amount       The amount of cryptocurrency to be bought.
     * @return true if the trader has enough funds, otherwise false.
     */
    public boolean canAfford(String currencyName, double amount) {
        CryptoCurrency currency = Market.getCurrency(currencyName);
        if (currency != null && amount > 0) {
            double cost = amount * currency.getPrice();
            return this.funds >= cost;
        }
        return false;
    }
    /**
     * Checks if the trader holds any cryptocurrency whose name starts with a given substring.
     *
     * @param substring The substring to check against the cryptocurrency names in the portfolio.
     * @return true if at least one cryptocurrency in the portfolio matches the substring.
     */
    public boolean hasCurrencyStartingWith(String substring) {
        for (String currencyName : portfolio.keySet()) {
            if (currencyName.startsWith(substring)) {
                // Return true if a cryptocurrency name starts with the given substring.
                return true;
            }
        }
        return false;
    }

    /**
     * Handles the logic for buying a cryptocurrency.
     *
     * @param currency The cryptocurrency to buy.
     * @param amount   The amount of the cryptocurrency to buy.
     * @return A string message indicating the outcome of the purchase attempt.
     */
    public String buyCrypto(CryptoCurrency currency, double amount) {
        double cost = amount * currency.getPrice();
        System.out.println("Cost: " + cost + ", Funds: " + funds); // Debug statement

        if (this.canAfford(currency.getName(), amount)) {
            funds -= cost; // Deduct the cost from the trader's funds.
            portfolio.put(currency.getName(), portfolio.getOrDefault(currency.getName(), 0.0) + amount);
            return "Purchased " + amount + " of " + currency.getName();
        }
        return "Insufficient funds to buy.";
    }



    /**
     * Handles the logic for selling a cryptocurrency.
     *
     * @param currency The cryptocurrency to sell.
     * @param amount   The amount of the cryptocurrency to sell.
     * @return A string message indicating the outcome of the sale attempt.
     */
    public String sellCrypto(CryptoCurrency currency, double amount) {
        Double holdings = portfolio.get(currency.getName());
        if (holdings != null && holdings >= amount) {
            double income = amount * currency.getPrice();
            funds += income; // Increase the trader's funds by the sale amount.
            // Update the portfolio to reflect the sold amount of cryptocurrency.
            portfolio.put(currency.getName(), holdings - amount);
            return "Sold " + amount + " of " + currency.getName();
        }
        return "Insufficient cryptocurrency to sell.";
    }

    /**
     * Returns the current funds of the trader.
     *
     * @return The amount of funds currently available.
     */
    public double getFunds() {
        return this.funds;
    }

    /**
     * Returns the current cryptocurrency portfolio of the trader.
     *
     * @return A map of cryptocurrency names to the amount held.
     */
    public Map<String, Double> getPortfolio() {
        return this.portfolio;
    }
}
