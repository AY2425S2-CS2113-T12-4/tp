package seedu.duke;

import seedu.duke.command.Command;
import seedu.duke.command.AddExpenseCommand;
import seedu.duke.command.AlertCommand;
import seedu.duke.command.CheckBudgetCommand;
import seedu.duke.command.DeleteCommand;
import seedu.duke.command.ExitCommand;
import seedu.duke.command.ListCommand;
import seedu.duke.command.SetBudgetCommand;
import seedu.duke.command.SummaryCommand;
import seedu.duke.exception.InvalidInputException;

import java.util.NoSuchElementException;

/**
 * The InputParser class is responsible for parsing the user input and converting it into the
 * corresponding command. It checks the input against predefined keywords and returns the appropriate
 * command to execute.
 */
public class InputParser {

    /**
     * Parses the user input and returns the corresponding Command based on the keyword entered.
     *
     * <p>This method splits the user input into a keyword and arguments. It then matches the
     * keyword against predefined commands and returns the corresponding Command object.
     * If the input is invalid, an InvalidInputException is thrown with a message prompting the user
     * to enter a valid command.</p>
     *
     * @param userInput The input entered by the user.
     * @return A Command object corresponding to the user input.
     * @throws InvalidInputException If the input does not match any valid command.
     * @throws NoSuchElementException If no input is provided or the input is malformed.
     */
    public Command parseInput(String userInput) throws InvalidInputException, NoSuchElementException {
        String[] splitLine = userInput.split(" ", 2);
        String keyword = splitLine[0].toLowerCase();

        return switch (keyword) {
        case "bye" -> new ExitCommand(userInput);
        case "add" -> new AddExpenseCommand(userInput);
        case "alert" -> new AlertCommand(userInput);
        case "summary" -> new SummaryCommand(userInput);
        case "list" -> new ListCommand(userInput);
        case "delete" -> new DeleteCommand(userInput);
        case "set-budget" -> new SetBudgetCommand(userInput);
        case "check-budget" -> new CheckBudgetCommand(userInput);
        default -> throw new InvalidInputException("Please try again with one of the valid commands:" +
                    "\nadd, alert, summary, list, delete, set-budget, bye");
        };
    }

}
