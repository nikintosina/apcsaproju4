import java.util.ArrayList;
import java.util.List;

/**
 * Represents a simulated market for cryptocurrencies.
 * Manages a list of cryptocurrencies and simulates daily price fluctuations.
 */
public class Market {
    private static List<CryptoCurrency> currencies;

    /**
     * Constructs a Market object with a predefined list of cryptocurrencies.
     * Initializes the market with default cryptocurrencies and their initial prices.
     */
    public Market() {
        currencies = new ArrayList<>();
        // Initialize with a predefined list of cryptocurrencies.
        currencies.add(new CryptoCurrency("Bitcoin", 10000.0));
        currencies.add(new CryptoCurrency("Ethereum", 5000.0));
        currencies.add(new CryptoCurrency("Litecoin", 200.0));
        currencies.add(new CryptoCurrency("Ripple", 0.5));
        currencies.add(new CryptoCurrency("Cardano", 1.2));
        // More cryptocurrencies can be added here.
    }

    /**
     * Simulates a day of market activity by randomly adjusting the prices of cryptocurrencies.
     * The price changes are within a predefined range to mimic market volatility.
     */
    public void simulateDay() {
        for (CryptoCurrency currency : currencies) {
            // Generate a random price fluctuation percentage between -5% and +5%.
            double changePercent = -0.05 + Math.random() * 0.1;
            // Update the price of the cryptocurrency based on this fluctuation.
            currency.updatePrice(currency.getPrice() * (1 + changePercent));
        }
    }

    /**
     * Retrieves a CryptoCurrency object by its name.
     *
     * @param name The name of the cryptocurrency to retrieve.
     * @return The CryptoCurrency object if found, otherwise null.
     */
    public static CryptoCurrency getCurrency(String name) {
        for (CryptoCurrency currency : currencies) {
            if (currency.getName().equalsIgnoreCase(name)) {
                // Return the cryptocurrency if the name matches.
                return currency;
            }
        }
        return null; // Return null if no matching cryptocurrency is found.
    }

    /**
     * Returns the list of all cryptocurrencies in the market.
     *
     * @return A list of CryptoCurrency objects in the market.
     */
    public List<CryptoCurrency> getCurrencies() {
        return this.currencies;
    }
}
