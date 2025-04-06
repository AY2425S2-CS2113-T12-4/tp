package budgetbuddy.command;

import budgetbuddy.model.BudgetManager;
import budgetbuddy.model.BudgetSummary;
import budgetbuddy.exception.InvalidInputException;
import budgetbuddy.parser.SummaryParser;

/**
 * The SummaryCommand class represents a command that generates a summary of the user's budgets and expenses.
 */
public class SummaryCommand extends Command {
    private final String[] categories;

    public SummaryCommand(String description) {
        super(description);
        // BudgetManager needed in parser to validate category existence, so we delay parsing to execute()
        this.categories = null; // initialize here; will be parsed in execute
    }

    /**
     * Executes the SummaryCommand by generating and displaying a summary of the user's budgets and expenses.
     */
    @Override
    public void execute(BudgetManager budgetManager) throws InvalidInputException {
        SummaryParser parser = new SummaryParser(description, budgetManager);
        String[] categories = parser.parse();

        BudgetSummary summary = new BudgetSummary(budgetManager);
        if (categories.length == 0) {
            summary.summariseBudget(); // all categories
        } else {
            summary.summariseBudget(categories); // selected categories
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
