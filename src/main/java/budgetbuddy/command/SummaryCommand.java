package budgetbuddy.command;

import budgetbuddy.model.BudgetManager;
import budgetbuddy.model.BudgetSummary;
import budgetbuddy.exception.InvalidInputException;
import budgetbuddy.parser.SummaryParser;

/**
 * The SummaryCommand class represents a command that generates a summary of the user's budgets and expenses.
 */
public class SummaryCommand extends Command {
    private String category;

    public SummaryCommand(String description) throws InvalidInputException {
        super(description);
        this.category = SummaryParser.parse(description);
    }

    /**
     * Executes the SummaryCommand by generating and displaying a summary of the user's budgets and expenses.
     */
    @Override
    public void execute(BudgetManager budgetManager) throws InvalidInputException {
        BudgetSummary budgetSummary = new BudgetSummary(budgetManager);
        budgetSummary.summariseBudget();
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
