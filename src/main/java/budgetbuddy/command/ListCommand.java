package budgetbuddy.command;

import budgetbuddy.model.BudgetManager;
import budgetbuddy.exception.InvalidInputException;
import budgetbuddy.parser.ListParser;

/**
 * The ListCommand class represents a command to list all expenses.
 *
 * <p>This command calls the {@link BudgetManager} to list all recorded expenses.</p>
 */
public class ListCommand extends Command{

    public ListCommand(String description) {

        super(description);
    }

    /**
     * Executes the ListCommand by requesting the {@link BudgetManager} to list all expenses.
     *
     * <p>This method interacts with the {@link BudgetManager} to display a list of all the expenses
     * stored in the system.</p>
     *
     * This also works when the user wants to find expenses in a range of date and time.
     *
     * @param budgetManager The BudgetManager responsible for managing budgets and expenses, used to list all expenses.
     * @throws InvalidInputException If the input cannot be processed, this exception will be thrown.
     */
    @Override
    public void execute(BudgetManager budgetManager) throws InvalidInputException {
        ListParser parser = new ListParser(description);
        String[] splitLine = parser.parse();
        String start = splitLine[0];
        String end = splitLine[1];

        if (start.isEmpty() && end.isEmpty()) {
            budgetManager.listAllExpenses();
        } else {
            budgetManager.listPartialExpenses(start, end);
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
