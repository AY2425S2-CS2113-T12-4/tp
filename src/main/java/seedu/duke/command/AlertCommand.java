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
        double amount = parser.parseAlertCommand(description);
        budgetManager.setBudgetAlert(amount);
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
