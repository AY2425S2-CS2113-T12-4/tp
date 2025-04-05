package budgetbuddy.command;

import budgetbuddy.exception.InvalidInputException;
import budgetbuddy.model.BudgetManager;
import budgetbuddy.parser.DeleteAlertParser;

/**
 * Represents a command to remove the current budget alert from the system.
 * <p>
 * The command follows the strict format: {@code delete-alert} with no additional parameters allowed.
 * This ensures clean removal of budget alerts without accidental parameter inclusion.
 * </p>
 *
 * @see budgetbuddy.model.BudgetManager#removeBudgetAlert()
 * @see budgetbuddy.parser.DeleteAlertParser
 */
public class DeleteAlertCommand extends Command {

    /**
     * Constructs a DeleteAlertCommand with the full command description.
     *
     * @param description The full command input string (should be exactly "delete-alert")
     */
    public DeleteAlertCommand(String description) {
        super(description);
    }

    /**
     * Executes the budget alert removal process after validating command syntax.
     * <ol>
     *   <li>Validates command format using {@link DeleteAlertParser}</li>
     *   <li>Removes the active budget alert via {@link BudgetManager}</li>
     * </ol>
     *
     * @param budgetManager The {@link BudgetManager} instance that handles budget operations
     * @throws InvalidInputException If the command contains extra parameters beyond "delete-alert"
     */
    @Override
    public void execute(BudgetManager budgetManager) throws InvalidInputException {
        // Step 1: Validate command syntax before execution
        new DeleteAlertParser(description).parse();

        // Step 2: Remove the active budget alert
        budgetManager.removeBudgetAlert();
    }

    /**
     * Indicates that executing this command should not terminate the application.
     *
     * @return Always returns {@code false} as this is a non-terminating command
     */
    @Override
    public boolean isExit() {
        return false;
    }
}