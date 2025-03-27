package budgetbuddy.command;

import budgetbuddy.model.BudgetManager;
import budgetbuddy.parser.Parser;
import budgetbuddy.exception.InvalidInputException;

/**
 * The AddExpenseCommand class represents a command that adds an expense to a specified budget.
 *
 * <p>This command parses the input description, extracts the amount, category, and description of the expense,
 * and then adds the expense to the specified budget using the BudgetManager.</p>
 */
public class AddExpenseCommand extends Command {

    public AddExpenseCommand(String description) {
        super(description);
    }

    /**
     * Executes the AddExpenseCommand by parsing the input and adding the expense to the appropriate budget.
     *
     * <p>The method first splits the input description into its components: the expense amount, category,
     * and description.
     * It then calls the {@link BudgetManager#addExpenseToBudget(String, double, String)} method to add the expense
     * to the specified category of the budget.</p>
     *
     * @param parser The parser used to handle and parse the user input.
     * @param budgetManager The BudgetManager responsible for managing expenses and budgets.
     * @throws InvalidInputException If there is invalid input while parsing the description or adding the expense.
     */
    @Override
    public void execute(Parser parser, BudgetManager budgetManager) throws InvalidInputException { //need to add UI and
        // Saving class
        String[] splitLine = parser.parseAddCommand(description);
        double amount = Double.parseDouble(splitLine[0]);
        String category = splitLine[1];
        String description = splitLine[2];
        budgetManager.addExpenseToBudget(category, amount, description);
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
