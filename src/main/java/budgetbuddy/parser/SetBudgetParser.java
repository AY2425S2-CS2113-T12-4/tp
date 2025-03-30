package budgetbuddy.parser;

import budgetbuddy.exception.InvalidInputException;

/**
 * Parses the "set-budget" command to extract category and amount.
 */
public class SetBudgetParser extends Parser<String[]> {
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
        String amount;

        if (parts.length == 2) {
            amount = parts[1];
        } else if (parts[1].startsWith("c/")) {
            category = parts[1].substring(2).trim();
            amount = parts[2];
        } else {
            throw new InvalidInputException("Use: set-budget [c/CATEGORY] <AMOUNT>");
        }

        return new String[]{category, amount};
    }
}
