package budgetbuddy.command;

import budgetbuddy.model.BudgetManager;
import budgetbuddy.parser.Parser;
import budgetbuddy.exception.InvalidInputException;

/**
 * The abstract Command class represents a general command that can be executed within the budget management system.
 *
 * <p>Each specific command will extend this class and implement the {@link #execute(Parser, BudgetManager)}
 * method to define its functionality. The class also contains an abstract method {@link #isExit()} to determine
 * if the command signals the exit of the program.</p>
 */
public abstract class Command {
    protected final String description;

    public Command(String description) {
        this.description = description;
    }

    /**
     * Executes the command by interacting with the provided Parser and BudgetManager.
     *
     * <p>This method is abstract and must be implemented by subclasses of Command. The subclasses should define
     * the specific behavior of the command, such as modifying the budget, adding an expense, etc.</p>
     *
     * @param parser The parser used to handle and parse user input.
     * @param budgetManager The BudgetManager that holds and manages budgets and expenses.
     * @throws InvalidInputException If there is invalid input while executing the command.
     */
    public abstract void execute(Parser parser, BudgetManager budgetManager) throws InvalidInputException;
    //need to add UI and Saving class

    /**
     * Determines if this command is an exit command that will terminate the program.
     *
     * @return {@code true} if the command signals the end of the program, {@code false} otherwise.
     */
    public abstract boolean isExit();

}
