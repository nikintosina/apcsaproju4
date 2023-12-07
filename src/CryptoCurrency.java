/**
 * Represents a cryptocurrency with a name and price.
 * This class is used to create and manage individual cryptocurrency objects.
 */
public class CryptoCurrency {
    private String name;
    private double price;

    /**
     * Constructs a CryptoCurrency object with a specified name and initial price.
     *
     * @param name The name of the cryptocurrency.
     * @param initialPrice The initial price of the cryptocurrency.
     */
    public CryptoCurrency(String name, double initialPrice) {
        this.name = name;
        this.price = initialPrice;
    }

    /**
     * Updates the price of the cryptocurrency.
     *
     * @param newPrice The new price of the cryptocurrency.
     */
    public void updatePrice(double newPrice) {
        this.price = newPrice;
    }

    /**
     * Returns the current price of the cryptocurrency.
     *
     * @return The current price of the cryptocurrency.
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * Returns the name of the cryptocurrency.
     *
     * @return The name of the cryptocurrency.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Provides a string representation of the CryptoCurrency object.
     * Useful for displaying the cryptocurrency's name.
     *
     * @return A string that represents the CryptoCurrency object.
     */
    public String toString() {
        return "CryptoCurrency{name='" + name + "', price=" + price + "}";
    }
}
