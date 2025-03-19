package seedu.duke;

import seedu.duke.exception.InvalidInputException;

public class Parser {

    public Parser(){}

    /**
     * Parses an "add" command to extract the amount, category, and description.
     * Expected format: add <AMOUNT> c/ <CATEGORY> d/ <DESCRIPTION>
     *
     * @param line The full command input.
     * @return A string array containing [amount, category, description].
     * @throws InvalidInputException If the format is incorrect.
     */
    String[] parseAddCommand(String line) throws InvalidInputException {
        if (line.length() < 4) {
            throw new InvalidInputException("Please use the format: add <AMOUNT> c/ <CATEGORY> d/ <DESCRIPTION>");
        }

        line = line.substring(4).trim();

        String[] splitAmountCategory = line.split("c/", 2);
        if (splitAmountCategory.length < 2) {
            throw new InvalidInputException("Please use the format: add <AMOUNT> c/ <CATEGORY> d/ <DESCRIPTION>");
        }

        String amount = splitAmountCategory[0].trim();
        String remaining = splitAmountCategory[1].trim();

        // Split by "/d" to separate Category and Description
        String[] splitCategoryDescription = remaining.split("d/", 2);
        if (splitCategoryDescription.length < 2) {
            throw new InvalidInputException("Please use the format: add <AMOUNT> c/ <CATEGORY> d/ <DESCRIPTION>");
        }

        String category = splitCategoryDescription[0].trim();
        String description = splitCategoryDescription[1].trim();

        return new String[]{amount, category, description};
    }

    /**
     * Parses a "set-budget" command to extract the budget category and amount.
     * Expected formats:
     * - "set-budget <AMOUNT>" (for Monthly budget)
     * - "set-budget c/CATEGORY <AMOUNT>" (for a specific category)
     *
     * @param command The full command input.
     * @return A string array containing [category, amount].
     * @throws InvalidInputException If the format is incorrect.
     */
    public String[] parseSetBudgetCommand(String command) throws InvalidInputException {
        String[] parts = command.split(" ");

        if (parts.length != 2 && parts.length != 3) {
            throw new InvalidInputException("Invalid command format. Use set-budget c/CATEGORY AMOUNT");
        }

        String category = "";
        String amount;

        try {
            if (parts.length == 2) { // Monthly budget setting
                amount = parts[1];
            } else if (parts.length == 3 && parts[1].startsWith("c/")) { // Category budget setting
                category = parts[1].substring(2).trim();
                amount = parts[2];
            } else {
                throw new InvalidInputException("Invalid command format. Use set-budget c/CATEGORY AMOUNT");
            }
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid amount format. Please enter a valid number.");
        }

        return new String[]{category, amount};
    }

    /**
     * Parses an "alert" command to extract the alert amount.
     * Expected format: alert <AMOUNT>
     *
     * @param command The full command input.
     * @return The alert amount as a double.
     * @throws InvalidInputException If the format is incorrect.
     */
    public double parseAlertCommand(String command) throws InvalidInputException {
        String[] parts = command.split(" ");
        if (parts.length != 2) {
            throw new InvalidInputException("Please use the format: alert <AMOUNT>");
        }
        return Double.parseDouble(parts[1].trim());
    }

    /**
     * Parses a "delete" command to extract the expense index to be deleted.
     * Expected format: delete <INDEX>
     *
     * @param command The full command input.
     * @return The index of the expense to delete.
     * @throws InvalidInputException If the format is incorrect.
     */
    public int parseDeleteCommand(String command) throws InvalidInputException {
        String[] parts = command.split(" ");
        if (parts.length != 2) {
            throw new InvalidInputException("Please use the format: alert <AMOUNT>");
        }
        return Integer.parseInt(parts[1].trim());
    }


}

