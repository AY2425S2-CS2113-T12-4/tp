package seedu.duke.command;

import seedu.duke.BudgetManager;
import seedu.duke.Parser;
import seedu.duke.exception.InvalidInputException;

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
     * <p>The method extracts the index from the description using the {@link Parser#parseDeleteCommand(String)} method,
     * and then calls the {@link BudgetManager#deleteExpense(int)} method to remove the expense from the budget.</p>
     *
     * @param parser The parser used to handle and parse the user input.
     * @param budgetManager The BudgetManager responsible for managing budgets and deleting the expense.
     * @throws InvalidInputException If there is invalid input while parsing the description or deleting the expense.
     */
    @Override
    public void execute(Parser parser, BudgetManager budgetManager) throws InvalidInputException {
        int index = parser.parseDeleteCommand(description);
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
