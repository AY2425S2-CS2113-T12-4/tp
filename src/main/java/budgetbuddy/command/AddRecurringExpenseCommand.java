package budgetbuddy.command;

import budgetbuddy.exception.InvalidInputException;
import budgetbuddy.model.BudgetManager;

public class AddRecurringExpenseCommand extends Command {

    public AddRecurringExpenseCommand(String description){
        super(description);
    }

    @Override
    public void execute(BudgetManager budgetManager) throws InvalidInputException {

    }


    @Override
    public boolean isExit() {
        return false;
    }
}
