package budgetbuddy.command;

import budgetbuddy.model.BudgetManager;
import budgetbuddy.exception.InvalidInputException;

/**
 * The ListCommand class represents a command to list all expenses.
 *
 * <p>This command calls the {@link BudgetManager} to list all recorded expenses.</p>
 */
public class ListCommand extends Command{

    public ListCommand(String description) {
        super(description);
    }

    /**
     * Executes the ListCommand by requesting the {@link BudgetManager} to list all expenses.
     *
     * <p>This method interacts with the {@link BudgetManager} to display a list of all the expenses
     * stored in the system.</p>
     *
     * This also works when the user wants to find expenses in a range of date and time.
     *
     * @param budgetManager The BudgetManager responsible for managing budgets and expenses, used to list all expenses.
     * @throws InvalidInputException If the input cannot be processed, this exception will be thrown.
     */
    @Override
    public void execute(BudgetManager budgetManager) throws InvalidInputException {

        boolean partialExpensesRequired=false;

        boolean hasStart = false;
        boolean hasEnd = false;
        //if start and end has been provided

        String start ="";

        String end ="";

        String[] listCommandSplit = this.description.split(" ");
        //this is required when finding expenses in a range


        for(int i = 0; i<listCommandSplit.length; i++){
            if(listCommandSplit[i].equalsIgnoreCase("start/")){
                hasStart = true;
                start = listCommandSplit[i+1];

                partialExpensesRequired=true;
            }if(listCommandSplit[i].equalsIgnoreCase("end/")){
                hasEnd = true;
                end = listCommandSplit[i+1];

                partialExpensesRequired=true;
            }
        }

        if(hasStart && !hasEnd) {
            start = this.description.split("start/")[1];
        }
        if(!hasStart && hasEnd) {
            end = this.description.split("end/")[1];
        }
        if(hasStart && hasEnd) {
            String  startToEnd = this.description.split("start/")[1];
            start = startToEnd.split("end/")[0];
            end = startToEnd.split("end/")[1];
        }

        if (partialExpensesRequired) {
            budgetManager.listPartialExpenses(start.trim(), end.trim());
        } else {
            budgetManager.listAllExpenses();
        }
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
