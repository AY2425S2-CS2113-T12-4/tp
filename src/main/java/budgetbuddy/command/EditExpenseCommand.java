package budgetbuddy.command;

import budgetbuddy.exception.InvalidInputException;
import budgetbuddy.model.BudgetManager;
import budgetbuddy.parser.EditExpenseParser;

/**
 * The EditExpenseCommand class represents a command to edit an existing expense.
 *
 * <p>This command parses the description to extract the index and optional fields such as
 * amount, description, and date/time. It then updates the corresponding expense in the BudgetManager.</p>
 */
public class EditExpenseCommand extends Command {

    public EditExpenseCommand(String description) {
        super(description);
    }

    /**
     * Executes the EditExpenseCommand by parsing the input and updating the specified expense.
     *
     * <p>The command supports optional editing of amount, description, and time </p>
     *
     * @param budgetManager The BudgetManager responsible for managing expenses.
     * @throws InvalidInputException If input is malformed or index is invalid.
     */
    @Override
    public void execute(BudgetManager budgetManager) throws InvalidInputException {
        EditExpenseParser parser = new EditExpenseParser(description);
        String[] splitLine = parser.parse();

        int index = Integer.parseInt(splitLine[0]);
        String amountStr = splitLine[1];
        String desc = splitLine[2];
        String dateTime = splitLine[3];

        double amount = amountStr.isEmpty() ? -1 : Double.parseDouble(amountStr);

        budgetManager.editExpense(index, amount, desc, dateTime);
    }

    /**
     * Indicates that this command does not terminate the program.
     *
     * @return {@code false} as EditExpenseCommand does not trigger program exit.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
