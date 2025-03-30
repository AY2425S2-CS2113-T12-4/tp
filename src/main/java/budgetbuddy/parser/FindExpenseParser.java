package budgetbuddy.parser;

import budgetbuddy.exception.InvalidInputException;

/**
 * Parses the "find" command to extract a search keyword.
 * Expected format: find <KEYWORD>
 */
public class FindExpenseParser extends Parser<String> {
    public FindExpenseParser(String input) {
        super(input);
    }

    @Override
    public String parse() throws InvalidInputException {
        String[] parts = input.split(" ");
        if (parts.length != 2) {
            throw new InvalidInputException("Use: find <KEYWORD>");
        }
        return parts[1].trim();
    }
}