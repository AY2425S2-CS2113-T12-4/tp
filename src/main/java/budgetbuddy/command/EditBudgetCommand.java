package budgetbuddy.command;

import budgetbuddy.model.BudgetManager;
import budgetbuddy.exception.InvalidInputException;
import budgetbuddy.parser.EditBudgetParser;

/**
 * The EditBudgetCommand class represents a command that edits an existing budget.
 *
 * <p>This command parses the input description, extracts the current budget name,
 * the new amount, and the new name of the budget (if provided).
 * It then updates the specified budget using the BudgetManager.</p>
 */
public class EditBudgetCommand extends Command {

    public EditBudgetCommand(String description) {
        super(description);
    }

    /**
     * Executes the EditBudgetCommand by parsing the input and editing the specified budget.
     *
     * <p>The method first splits the input description into its components: the current budget name,
     * the new amount (if provided), and the new name (if provided).
     * It then calls the {@link BudgetManager#editBudget(String, double, String)}
     * method to update the budget information.</p>
     *
     * @param budgetManager The BudgetManager responsible for managing budgets and expenses.
     * @throws InvalidInputException If there is invalid input while parsing the description or editing the budget.
     */
    @Override
    public void execute(BudgetManager budgetManager) throws InvalidInputException {
        EditBudgetParser parser = new EditBudgetParser(description);
        String[] parsedInput = parser.parse();
        String currentName = parsedInput[0];
        double newAmount = parsedInput[1].isEmpty() ? -1 : Double.parseDouble(parsedInput[1]);
        String newName = parsedInput[2];

        budgetManager.editBudget(currentName, newAmount, newName);
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
