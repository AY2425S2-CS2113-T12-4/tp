package budgetbuddy.command;

import budgetbuddy.model.BudgetManager;
import budgetbuddy.parser.AddParser;
import budgetbuddy.exception.InvalidInputException;

/**
 * The AddExpenseCommand class represents a command that adds an expense to a specified budget.
 *
 * <p>This command parses the input description, extracts the amount, category, description of the expense,
 * and time of expense.
 * It then adds the expense to the specified budget using the BudgetManager.</p>
 */
public class AddExpenseCommand extends Command {

    public AddExpenseCommand(String description) {
        super(description);
    }

    /**
     * Executes the AddExpenseCommand by parsing the input and adding the expense to the appropriate budget.
     *
     * <p>The method first splits the input description into its components: the expense amount, category,
     * description and time.
     * It then calls the {@link BudgetManager#addExpenseToBudget(String, double, String, String)}
     * method to add the expense
     * to the specified category of the budget.</p>
     *
     * @param budgetManager The BudgetManager responsible for managing expenses and budgets.
     * @throws InvalidInputException If there is invalid input while parsing the description or adding the expense.
     */
    @Override
    public void execute(BudgetManager budgetManager) throws InvalidInputException { //need to add UI and
        // Saving class
        AddParser parser = new AddParser(description);
        String[] splitLine = parser.parse();
        double amount = Double.parseDouble(splitLine[0]);
        String category = splitLine[1];
        String description = splitLine[2];
        String time = splitLine[3];

        budgetManager.addExpenseToBudget(category, amount, description, time);
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
