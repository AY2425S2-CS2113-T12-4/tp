package budgetbuddy.command;

import budgetbuddy.model.BudgetManager;
import budgetbuddy.parser.Parser;
import budgetbuddy.exception.InvalidInputException;

/**
 * The CheckBudgetCommand class represents a command that checks the budget for a specific category.
 *
 * <p>This command parses the input description to extract the category name and then checks the budget for that
 * category using the {@link BudgetManager#checkBudget(String)} method.</p>
 */
public class CheckBudgetCommand extends Command{

    public CheckBudgetCommand(String description) {
        super(description);
    }

    /**
     * Executes the CheckBudgetCommand by parsing the input to get the category and then checking the budget for that
     * category.
     *
     * <p>The method extracts the category from the description using the {@link Parser#parseCheckBudgetCommand(String)}
     * method, and then calls the {@link BudgetManager#checkBudget(String)} method to check the budget for that
     * category.</p>
     *
     * @param parser The parser used to handle and parse the user input.
     * @param budgetManager The BudgetManager responsible for managing budgets and checking the budget for a category.
     * @throws InvalidInputException If there is invalid input while parsing the description or checking the budget.
     */
    @Override
    public void execute(Parser parser, BudgetManager budgetManager) throws InvalidInputException {
        String category = parser.parseCheckBudgetCommand(description);
        budgetManager.checkBudget(category);
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
