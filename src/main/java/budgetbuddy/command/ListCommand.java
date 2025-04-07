package budgetbuddy.command;

import budgetbuddy.model.BudgetManager;
import budgetbuddy.exception.InvalidInputException;

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
        boolean hasStart = description.contains("start/");
        boolean hasEnd = description.contains("end/");
        boolean partialExpensesRequired = hasStart || hasEnd;

        String start = "";
        String end = "";

        boolean invalidStart = false;
        boolean invalidEnd = false;

        if (hasStart) {
            String[] splitStart = description.split("start/", 2);
            if (splitStart.length < 2 || splitStart[1].isBlank() || splitStart[1].trim().startsWith("end/")) {
                invalidStart = true;
            } else {
                start = splitStart[1].split("end/")[0].trim();
            }
        }

        if (hasEnd) {
            String[] splitEnd = description.split("end/", 2);
            if (splitEnd.length < 2 || splitEnd[1].isBlank()) {
                invalidEnd = true;
            } else {
                end = splitEnd[1].trim();
            }
        }

        if ((invalidStart || invalidEnd) && partialExpensesRequired) {
            System.err.print("Warning:");
            if (invalidStart) {
                System.err.print(" start/ marker is empty.");
            }
            if (invalidEnd) {
                System.err.print(" end/ marker is empty.");
            }
            System.err.println(" Showing full list.");
            budgetManager.listAllExpenses();
            return;
        }

        if (partialExpensesRequired) {
            budgetManager.listPartialExpenses(start, end);
        } else {
            budgetManager.listAllExpenses();
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