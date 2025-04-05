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
        boolean hasStart = false;
        boolean hasEnd = false;
        String start = "";
        String end = "";

        String[] listCommandSplit = this.description.split(" ");

        // Extract start and end times
        for (String part : listCommandSplit) {
            String lowerPart = part.toLowerCase();
            if (lowerPart.startsWith("start/")) {
                hasStart = true;
                start = part.substring("start/".length()).trim();
            } else if (lowerPart.startsWith("end/")) {
                hasEnd = true;
                end = part.substring("end/".length()).trim();
            }
        }

        // Validate the inputs
        if (hasStart || hasEnd) {
            // Handle missing partner time
            if (hasStart && !hasEnd) {
                throw new InvalidInputException("Missing end time. Please use format: " +
                        "list start/MMM dd yyyy 'at' HH:mm end/MMM dd yyyy 'at' HH:mm");
            }
            if (!hasStart && hasEnd) {
                throw new InvalidInputException("Missing start time. Please use format: " +
                        "list start/MMM dd yyyy 'at' HH:mm end/MMM dd yyyy 'at' HH:mm");
            }

            // Handle empty times
            if (start.isEmpty() && end.isEmpty()) {
                throw new InvalidInputException("Both start and end times are empty. " +
                        "Please specify times in format: MMM dd yyyy 'at' HH:mm");
            }
            if (start.isEmpty()) {
                throw new InvalidInputException("Start time cannot be empty. Format: MMM dd yyyy 'at' HH:mm");
            }
            if (end.isEmpty()) {
                throw new InvalidInputException("End time cannot be empty. Format: MMM dd yyyy 'at' HH:mm");
            }

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
