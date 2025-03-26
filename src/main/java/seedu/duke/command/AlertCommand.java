package seedu.duke.command;

import seedu.duke.BudgetManager;
import seedu.duke.Parser;
import seedu.duke.exception.InvalidInputException;

/**
 * The AlertCommand class represents a command that sets an alert for a specific budget amount.
 *
 * <p>This command parses the input description to extract the alert amount and then sets the alert using the
 * {@link BudgetManager#setBudgetAlert(double)} method.</p>
 */
public class AlertCommand extends Command{

    public AlertCommand(String description) {
        super(description);
    }

    /**
     * Executes the AlertCommand by parsing the input and setting the alert for the budget.
     *
     * <p>The method extracts the alert amount from the description using the {@link Parser#parseAlertCommand(String)}
     * method, and then calls the {@link BudgetManager#setBudgetAlert(double)} method to set the alert for the budget.
     * </p>
     *
     * @param parser The parser used to handle and parse the user input.
     * @param budgetManager The BudgetManager responsible for managing budgets and setting alerts.
     * @throws InvalidInputException If there is invalid input while parsing the description or setting the alert.
     */

    @Override
    public void execute(Parser parser, BudgetManager budgetManager) throws InvalidInputException {
        String[] parts = description.trim().split(" ", 2);

        if (parts.length < 2) {
            throw new InvalidInputException("Invalid alert command. Try: alert set/edit/remove <AMOUNT>");
        }

        String subInput = parts[1]; // "set 5000", "edit 3000", etc.
        String[] subParts = subInput.trim().split(" ");

        String subcommand = subParts[0].toLowerCase();

        switch (subcommand) {
        case "set":
        case "edit": {
            if (subParts.length != 2) {
                throw new InvalidInputException("Usage: alert set/edit <AMOUNT>");
            }
            double amount = parser.parseDouble(subParts[1]);
            if (amount < 0) {
                throw new InvalidInputException("Alert amount must be a non-negative number.");
            }

            budgetManager.setBudgetAlert(amount);
            break;
        }
        case "remove": {
            budgetManager.setBudgetAlert(0);
            break;
        }
        default:
            throw new InvalidInputException("Unknown alert subcommand. Use: set, edit, or remove.");
        }
    }

    /**
     * Returns {@code false} as this command does not signify the end of the program.
     *
     * @return {@code false} to indicate the program should not exit after executing this command.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
