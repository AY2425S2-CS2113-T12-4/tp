package budgetbuddy.command;

import budgetbuddy.model.BudgetManager;
import budgetbuddy.parser.Parser;
import budgetbuddy.exception.InvalidInputException;

/**
 * The SetBudgetCommand class represents a command to set a budget for a specific category.
 *
 * <p>This command allows the user to set a budget for a given category by interacting with the
 * {@link BudgetManager} to store the specified budget value for that category.</p>
 */
public class SetBudgetCommand extends Command{
    
    public SetBudgetCommand(String description) {
        super(description);
    }

    /**
     * Executes the SetBudgetCommand by setting a budget for the specified category.
     *
     * <p>This method parses the description for category and amount, then delegates the task of
     * setting the budget to the {@link BudgetManager}.</p>
     *
     * @param parser The parser used to handle and parse the user input.
     * @param budgetManager The BudgetManager responsible for managing budgets and expenses.
     * @throws InvalidInputException If the input is invalid (e.g., parsing errors), this exception will be thrown.
     */
    @Override
    public void execute(Parser parser, BudgetManager budgetManager) throws InvalidInputException {
        String[] splitline = parser.parseSetBudgetCommand(description);
        String category = splitline[0];
        double amount = Double.parseDouble(splitline[1]);
        budgetManager.setBudget(category, amount);
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
