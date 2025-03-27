package budgetbuddy.command;

import budgetbuddy.model.BudgetManager;
import budgetbuddy.model.BudgetSummary;
import budgetbuddy.parser.Parser;
import budgetbuddy.exception.InvalidInputException;

/**
 * The SummaryCommand class represents a command that generates a summary of the user's budgets and expenses.
 *
 * <p>This command allows the user to get a summary of the current budget status, including the details of all the
 * budgets and expenses. It interacts with the {@link BudgetSummary} class to produce the summary from the
 * data stored in the {@link BudgetManager}.</p>
 */
public class SummaryCommand extends Command{
    public SummaryCommand(String description) {
        super(description);
    }

    /**
     * Executes the SummaryCommand by generating and displaying a summary of the user's budgets and expenses.
     *
     * <p>This method creates a {@link BudgetSummary} instance and calls its {@link BudgetSummary#summariseBudget()}
     * method to generate and display the summary based on the data in the {@link BudgetManager}.</p>
     *
     * @param parser The parser used to handle and parse the user input (not used in this case).
     * @param budgetManager The BudgetManager responsible for managing budgets and expenses.
     * @throws InvalidInputException If the input is invalid (e.g., unexpected errors occur), this exception will be
     *     thrown.
     */
    @Override
    public void execute(Parser parser, BudgetManager budgetManager) throws InvalidInputException {
        BudgetSummary budgetSummary = new BudgetSummary(budgetManager);
        budgetSummary.summariseBudget();
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
