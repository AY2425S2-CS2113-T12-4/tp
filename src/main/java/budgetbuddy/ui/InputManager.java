package budgetbuddy.ui;

import java.util.NoSuchElementException;
import java.util.Scanner;

import budgetbuddy.command.Command;
import budgetbuddy.exception.InvalidInputException;
import budgetbuddy.model.BudgetManager;
import budgetbuddy.parser.InputParser;
import budgetbuddy.parser.Parser;


/**
 * The InputManager class is responsible for managing and processing user input in the application.
 * It continuously listens for input and processes commands related to managing budgets and expenses.
 * The loop continues until the user types the "bye" command, which will terminate the input loop.
 *
 * The class interacts with the BudgetManager and uses the Parser and InputParser to handle different
 * input formats and commands. It is also responsible for catching and handling exceptions related to
 * invalid user input, ensuring the program does not crash due to errors.
 *
 * <p>Example usage:</p>
 * <pre>
 *     InputManager inputManager = new InputManager(budgetManager);
 *     inputManager.processInputLoop();
 * </pre>
 *
 * @see Command
 * @see InvalidInputException
 * @see Parser
 * @see InputParser
 */
public class InputManager {
    private final BudgetManager budgetManager;
    private final InputParser inputParser;


    /**
     * Constructs an InputManager that interacts with the specified BudgetManager.
     *
     * @param budgetManager The BudgetManager instance to be used for managing budgets and expenses.
     */
    public InputManager(BudgetManager budgetManager) {
        assert budgetManager != null : "BudgetManager cannot be null.";
        this.budgetManager = budgetManager;
        inputParser = new InputParser();
    }

    /**
     * Starts an input processing loop where the user can enter commands.
     * The loop continues until the user types "bye", at which point the program will exit.
     *
     * <p>During the loop, the user input is parsed and processed. The loop handles various
     * exceptions, such as invalid input or formatting errors, and provides appropriate feedback
     * to the user.</p>
     *
     * @throws NoSuchElementException if no input is found during the process.
     * @throws InvalidInputException if the user provides invalid input.
     * @throws NumberFormatException if a number format is invalid in the input.
     */
    public void processInputLoop() {
        String line;
        Scanner in = new Scanner(System.in);

        boolean isExit = false;
        while (!isExit) {
            try {
                line = in.nextLine().trim();
                Command c = inputParser.parseInput(line);
                c.execute(budgetManager);
                isExit = c.isExit();
            } catch (InvalidInputException e) {
                e.print();
            } catch (NumberFormatException e) {
                Ui.printSeparator();
                System.out.println("Invalid input: Please enter a valid number.");
                Ui.printSeparator();
            } catch (NoSuchElementException e) {
                Ui.printSeparator();
                System.out.println("No input found, exiting...");
                Ui.printSeparator();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        in.close();

    }
}
