package budgetbuddy.parser;

import budgetbuddy.exception.InvalidInputException;

/**
 * Parses the "alert" command to extract the alert threshold amount.
 */
public class AlertParser extends Parser<Double> {
    public AlertParser(String input) {
        super(input);
    }

    @Override
    public Double parse() throws InvalidInputException {
        String[] parts = input.split(" ");
        if (parts.length != 2) {
            throw new InvalidInputException("Use: alert <AMOUNT>");
        }
        return Double.parseDouble(parts[1].trim());
    }
}
