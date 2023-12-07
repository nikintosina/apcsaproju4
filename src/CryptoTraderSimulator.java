import java.util.Scanner;

/**
 * Main class for the CryptoTraderSimulator.
 * This class handles the user interface for a cryptocurrency trading simulation,
 * managing user inputs and displaying outputs based on the market and trader actions.
 */
public class CryptoTraderSimulator {
    public static void main(String[] args) {
        // Initializing the market and a trader with a starting budget.
        Market market = new Market();
        Trader trader = new Trader(10000); // Starting with $10,000
        Scanner scanner = new Scanner(System.in);

        // Running the trading simulation for 30 days.
        for (int day = 0; day < 3; day++) {
            market.simulateDay(); // Simulating daily price changes in the market.
            System.out.println("Day " + (day + 1));

            // Displaying the current market prices for each cryptocurrency.
            System.out.println("Market Prices:");
            for (CryptoCurrency currency : market.getCurrencies()) {
                System.out.println(currency.getName() + ": $" + currency.getPrice());
            }

            // Showing the trader's current funds and portfolio.
            System.out.println("Your funds: $" + trader.getFunds());
            System.out.println("Your portfolio: " + trader.getPortfolio());

            boolean validInput = false;
            String name = "";
            while (!validInput) {
                // Getting the user input for the cryptocurrency they wish to trade.
                System.out.println("Enter the full name or the first letter of the cryptocurrency you want to trade:");
                String input = scanner.next();

                name = getNameForAction(input, market);
                if (name.isEmpty()) {
                    System.out.println("Invalid cryptocurrency name or initial. Please try again.");
                } else {
                    validInput = true; // Valid cryptocurrency found.
                }
            }

            // Allowing the user to make a decision: buy, sell, or pass.
            System.out.println("Choose an action: 1) Buy  2) Sell  3) Pass");
            int choice = scanner.nextInt();

            // Handling the user's chosen action.
            handleUserAction(scanner, market, trader, name, choice);

            // Ending the game if the trader runs out of funds.
            if (trader.getFunds() <= 0) {
                System.out.println("Game over. You've run out of funds.");
                break;
            }
        }

        // Calculating and displaying the final value of the trader's portfolio.
        double totalValue = calculateFinalPortfolioValue(trader, market);
        System.out.println("Game over. Your final portfolio value: $" + totalValue);
        scanner.close();
    }

    /**
     * Displays whether the trader's portfolio contains cryptocurrencies starting with a specific letter.
     *
     * @param letter The letter to check in the cryptocurrency names.
     * @param trader The trader whose portfolio is being checked.
     */
    private static void displayCryptocurrencyStartingWith(String letter, Trader trader) {
        if (trader.hasCurrencyStartingWith(letter)) {
            System.out.println("You have a cryptocurrency starting with '" + letter + "'.");
        } else {
            System.out.println("You don't have any cryptocurrency starting with '" + letter + "'.");
        }
    }

    /**
     * Handles the user's action based on their choice to buy, sell, or pass.
     *
     * @param scanner The Scanner object for user input.
     * @param market  The market where the trading occurs.
     * @param trader  The trader performing the action.
     * @param input   The user's input for the cryptocurrency name or its first letter.
     * @param choice  The user's choice of action.
     * @return true if an action was successfully taken, false otherwise.
     */
    private static boolean handleUserAction(Scanner scanner, Market market, Trader trader, String input, int choice) {
        String name = getNameForAction(input, market);
        if (!name.isEmpty()) {
            if (choice == 1) { // Buy
                System.out.println("Enter amount to buy:");
                double amount = scanner.nextDouble();
                if (trader.canAfford(name, amount)) {
                    CryptoCurrency currency = market.getCurrency(name);
                    if (currency != null) {
                        return processBuy(trader, currency, amount);
                    }
                } else {
                    System.out.println("Insufficient funds to buy.");
                }
            } else if (choice == 2) { // Sell
                System.out.println("Enter amount to sell:");
                double amount = scanner.nextDouble();
                CryptoCurrency currency = market.getCurrency(name);
                if (currency != null) {
                    return processSell(trader, currency, amount);
                } else {
                    System.out.println("Insufficient cryptocurrency to sell.");
                }
            }
        }
        return false;
    }

    /**
     * Processes the buying action for a trader.
     *
     * @param trader   The trader performing the buy action.
     * @param currency The cryptocurrency to be bought.
     * @param amount   The amount of cryptocurrency to buy.
     * @return true if the purchase was successful, false otherwise.
     */
    private static boolean processBuy(Trader trader, CryptoCurrency currency, double amount) {
        String result = trader.buyCrypto(currency, amount);
        System.out.println(result);
        return result.startsWith("Purchased");
    }

    /**
     * Processes the selling action for a trader.
     *
     * @param trader   The trader performing the sell action.
     * @param currency The cryptocurrency to be sold.
     * @param amount   The amount of cryptocurrency to sell.
     * @return true if the sale was successful, false otherwise.
     */
    private static boolean processSell(Trader trader, CryptoCurrency currency, double amount) {
        String result = trader.sellCrypto(currency, amount);
        System.out.println(result);
        return result.startsWith("Sold");
    }

    /**
     * Retrieves the name of the cryptocurrency based on user input.
     *
     * @param input  The user input, either the full name or the first letter of the cryptocurrency.
     * @param market The market from which to retrieve the cryptocurrency name.
     * @return The cryptocurrency name if found, otherwise an empty string.
     */
    private static String getNameForAction(String input, Market market) {
        for (CryptoCurrency currency : market.getCurrencies()) {
            if ((input.length() == 1 && currency.getName().toLowerCase().startsWith(input.toLowerCase())) ||
                    currency.getName().equals(input)) {
                return currency.getName();
            }
        }
        return ""; // Return empty string if no match is found.
    }


    /**
     * Calculates the final value of the trader's portfolio.
     *
     * @param trader The trader whose portfolio value is being calculated.
     * @param market The market from which cryptocurrency prices are retrieved.
     * @return The total value of the trader's portfolio.
     */
    private static double calculateFinalPortfolioValue(Trader trader, Market market) {
        double totalValue = trader.getFunds();
        for (String currencyName : trader.getPortfolio().keySet()) {
            CryptoCurrency currency = market.getCurrency(currencyName);
            totalValue += trader.getPortfolio().get(currencyName) * currency.getPrice();
        }
        return totalValue;
    }
}
