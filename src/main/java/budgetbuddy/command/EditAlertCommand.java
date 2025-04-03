package budgetbuddy.command;

import budgetbuddy.exception.InvalidInputException;
import budgetbuddy.model.BudgetManager;
import budgetbuddy.parser.AlertParser;

/**
 * Command to edit an existing budget alert amount.
 */
public class EditAlertCommand extends Command {

    public EditAlertCommand(String description) {
        super(description);
    }

    /**
     * Executes the edit alert command.
     * Parses the new amount and updates the alert.
     *
     * @param budgetManager The BudgetManager instance handling budget logic.
     * @throws InvalidInputException If input format is incorrect or invalid.
     */
    @Override
    public void execute(BudgetManager budgetManager) throws InvalidInputException {
        AlertParser parser = new AlertParser(description);
        double newAmount = parser.parse();
        budgetManager.getBudgetAlert().editAlertAmount(newAmount);
    }

    /**
     * Indicates that the app should continue running after this command.
     *
     * @return false
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
