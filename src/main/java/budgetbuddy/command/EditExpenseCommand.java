package budgetbuddy.command;

import budgetbuddy.exception.InvalidInputException;
import budgetbuddy.model.BudgetManager;
import budgetbuddy.parser.Parser;

public class EditExpenseCommand extends Command{
    public EditExpenseCommand(String description) {
        super(description);
    }

    @Override
    public void execute(Parser parser, BudgetManager budgetManager) throws InvalidInputException {
        String[] splitLine = parser.parseEditExpenseCommand(description);
        int index = Integer.parseInt(splitLine[0]);
        double amount = Double.parseDouble(splitLine[1]);
        String description = splitLine[2];
        String dateTime = splitLine[3];
        budgetManager.editExpense(index, amount, description, dateTime);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
