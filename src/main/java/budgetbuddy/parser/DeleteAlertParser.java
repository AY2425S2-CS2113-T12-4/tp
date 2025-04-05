package budgetbuddy.parser;

import budgetbuddy.exception.InvalidInputException;

/**
 * Parses input for the "delete-alert" command.
 * Throws InvalidInputException if additional parameters are provided.
 */
public class DeleteAlertParser extends Parser<String> {
    private static final String COMMAND_SYNTAX = "delete-alert";

    public DeleteAlertParser(String input) {
        super(input);
    }

    /**
     * Validates the delete-alert command format.
     * @return Empty string if validation succeeds
     * @throws InvalidInputException if command contains extra parameters
     */
    @Override
    public String parse() throws InvalidInputException {
        String normalizedInput = input.trim();

        if (!normalizedInput.equals(COMMAND_SYNTAX)) {
            throw new InvalidInputException(
                    String.format("Invalid format. Use exactly: '%s'", COMMAND_SYNTAX));
        }

        return ""; // No parameters to return
    }
}
