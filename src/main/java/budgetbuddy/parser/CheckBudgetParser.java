package budgetbuddy.parser;

import budgetbuddy.exception.InvalidInputException;

/**
 * Parses the "check-budget" command to extract category if provided.
 * Expected formats: check-budget OR check-budget c/<CATEGORY>
 */
public class CheckBudgetParser extends Parser<String> {
    public CheckBudgetParser(String input) {
        super(input);
    }

    @Override
    public String parse() throws InvalidInputException {
        String[] parts = input.split(" ");
        if (parts.length == 1) {
            return "";
        }
        if (parts.length == 2 && parts[1].startsWith("c/")) {
            return parts[1].substring(2).trim();
        }
        throw new InvalidInputException("Invalid format. Use: check-budget [c/CATEGORY]");
    }
}