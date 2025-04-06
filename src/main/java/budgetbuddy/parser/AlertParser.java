package budgetbuddy.parser;

import budgetbuddy.exception.InvalidInputException;

/**
 * Parses the "alert" command to extract the alert threshold amount.
 */
public class AlertParser extends Parser<Double> {
    private static final double MAX_ALERT_AMOUNT = 100000; // Define a maximum alert amount limit

    public AlertParser(String input) {
        super(input);
    }

    @Override
    public Double parse() throws InvalidInputException {
        String[] parts = input.split(" ");
        if (parts.length != 2) {
            throw new InvalidInputException("Use: alert <AMOUNT>");
        }

        try {
            double amount = Double.parseDouble(parts[1].trim());
            if (amount < 0 || amount > MAX_ALERT_AMOUNT) {
                throw new InvalidInputException("Alert amount must be between 0 and " + MAX_ALERT_AMOUNT);
            }
            return amount;
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid amount format");
        }
    }
}
