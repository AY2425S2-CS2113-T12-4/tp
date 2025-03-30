package budgetbuddy.command;

import budgetbuddy.model.BudgetManager;
import budgetbuddy.parser.DeleteParser;
import budgetbuddy.exception.InvalidInputException;

/**
 * The DeleteCommand class represents a command that deletes an expense from a budget.
 *
 * <p>This command parses the input description to extract the index of the expense to be deleted and
 * then deletes the corresponding expense using the {@link BudgetManager#deleteExpense(int)} method.</p>
 */
public class DeleteCommand extends Command{

    public DeleteCommand(String description) {
        super(description);
    }

    /**
     * Executes the DeleteCommand by parsing the input to get the index of the expense to be deleted
     * and then deleting the expense at that index.
     *
     * <p>The method extracts the index from the description and then calls the
     * {@link BudgetManager#deleteExpense(int)} method to remove the expense from the budget.
     * </p>
     *
     * @param budgetManager The BudgetManager responsible for managing budgets and deleting the expense.
     * @throws InvalidInputException If there is invalid input while parsing the description or deleting the expense.
     */
    @Override
    public void execute(BudgetManager budgetManager) throws InvalidInputException {
        DeleteParser parser = new DeleteParser(description);
        int index = parser.parse();
        budgetManager.deleteExpense(index);
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
