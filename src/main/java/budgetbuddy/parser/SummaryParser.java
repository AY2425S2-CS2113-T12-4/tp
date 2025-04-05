package budgetbuddy.parser;

import budgetbuddy.exception.InvalidInputException;

public class SummaryParser {
    private static final String CATEGORY_PREFIX = "c/";

    public static String parse(String input) throws InvalidInputException {
        String trimmedInput = input.trim();

        // Handle empty input or just "summary"
        if (trimmedInput.isEmpty() || trimmedInput.equals("summary")) {
            return null;
        }

        // Check for valid category format
        if (!trimmedInput.startsWith("summary " + CATEGORY_PREFIX)) {
            throw new InvalidInputException("Use: summary [c/CATEGORY]");
        }

        // Extract category
        String categoryPart = trimmedInput.substring(("summary " + CATEGORY_PREFIX).length()).trim();
        if (categoryPart.isEmpty()) {
            throw new InvalidInputException("Category cannot be empty. Use: summary c/CATEGORY");
        }

        return categoryPart;
    }
}
