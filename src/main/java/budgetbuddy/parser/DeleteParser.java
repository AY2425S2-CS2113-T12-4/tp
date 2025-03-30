package budgetbuddy.parser;

import budgetbuddy.exception.InvalidInputException;

/**
 * Parses the "delete" command to extract the index of the expense.
 * Expected format: delete <INDEX>
 */
public class DeleteParser extends Parser<Integer> {
    public DeleteParser(String input) {
        super(input);
    }

    @Override
    public Integer parse() throws InvalidInputException {
        String[] parts = input.split(" ");
        if (parts.length != 2) {
            throw new InvalidInputException("Use: delete <INDEX>");
        }
        return Integer.parseInt(parts[1].trim());
    }
}