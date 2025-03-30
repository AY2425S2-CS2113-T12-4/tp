package budgetbuddy.command;

import budgetbuddy.model.BudgetManager;
import budgetbuddy.ui.Ui;

/**
 * The ExitCommand class represents a command that exits the program.
 *
 * <p>This command simply prints "Bye" to the console and signals the termination of the program.</p>
 */
public class ExitCommand extends Command{

    public ExitCommand(String description) {
        super(description);
    }

    /**
     * Executes the ExitCommand by printing a goodbye message to the console.
     *
     * <p>This method does not interact with the {@link BudgetManager}  as it simply
     * terminates the program by printing "Bye".</p>
     *
     * @param budgetManager The BudgetManager responsible for managing budgets and expenses (not used in this case).
     */
    @Override
    public void execute(BudgetManager budgetManager) {
        Ui.printGoodbyeMessage();
    }

    /**
     * Returns {@code true} as this command signifies the end of the program.
     *
     * @return {@code true} to indicate that the program should exit after executing this command.
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
