package budgetbuddy.parser;

import budgetbuddy.exception.InvalidInputException;

/**
 * Parses the "set-budget" command to extract category and amount.
 */
public class SetBudgetParser extends Parser<String[]> {
    private static final double MAX_AMOUNT = 100000; // Define a maximum amount limit

    public SetBudgetParser(String input) {
        super(input);
    }

    @Override
    public String[] parse() throws InvalidInputException {
        String[] parts = input.split(" ");
        if (parts.length != 2 && parts.length != 3) {
            throw new InvalidInputException("Use: set-budget [c/CATEGORY] <AMOUNT>");
        }

        String category = "";
        String amountStr;

        if (parts.length == 2) {
            amountStr = parts[1];
        } else if (parts[1].startsWith("c/")) {
            category = parts[1].substring(2).trim();
            amountStr = parts[2];
        } else {
            throw new InvalidInputException("Use: set-budget [c/CATEGORY] <AMOUNT>");
        }

        try {
            double amount = Double.parseDouble(amountStr);
            if (amount < 0 || amount > MAX_AMOUNT) {
                throw new InvalidInputException("Amount must be between 0 and " + MAX_AMOUNT);
            }
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid amount format");
        }

        return new String[]{category, amountStr};
    }
}
