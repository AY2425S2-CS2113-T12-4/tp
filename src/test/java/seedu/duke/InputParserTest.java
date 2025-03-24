package seedu.duke;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.command.AddExpenseCommand;
import seedu.duke.command.AlertCommand;
import seedu.duke.command.Command;
import seedu.duke.command.SummaryCommand;
import seedu.duke.command.ListCommand;
import seedu.duke.command.DeleteCommand;
import seedu.duke.command.SetBudgetCommand;
import seedu.duke.command.CheckBudgetCommand;
import seedu.duke.exception.InvalidInputException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InputParserTest {

    private InputParser inputParser;

    @BeforeEach
    public void setUp() {
        inputParser = new InputParser();
    }

    @Test
    public void testParseInput_validCommands() throws InvalidInputException, NoSuchElementException {

        Command addCommand = inputParser.parseInput("add 50 c/ food d/ lunch");
        assertTrue(addCommand instanceof AddExpenseCommand, "Expected AddExpenseCommand");

        Command alertCommand = inputParser.parseInput("alert 100");
        assertTrue(alertCommand instanceof AlertCommand, "Expected AlertCommand");

        Command summaryCommand = inputParser.parseInput("summary");
        assertTrue(summaryCommand instanceof SummaryCommand, "Expected SummaryCommand");

        Command listCommand = inputParser.parseInput("list");
        assertTrue(listCommand instanceof ListCommand, "Expected ListCommand");

        Command deleteCommand = inputParser.parseInput("delete 1");
        assertTrue(deleteCommand instanceof DeleteCommand, "Expected DeleteCommand");

        Command setBudgetCommand = inputParser.parseInput("set-budget c/ food 100");
        assertTrue(setBudgetCommand instanceof SetBudgetCommand, "Expected SetBudgetCommand");

        Command checkBudgetCommand = inputParser.parseInput("check-budget");
        assertTrue(checkBudgetCommand instanceof CheckBudgetCommand, "Expected CheckBudgetCommand");
    }

    @Test
    public void testParseInput_invalidCommand() {
        assertThrows(InvalidInputException.class, () -> {
            inputParser.parseInput("invalid-command");
        }, "Expected InvalidInputException to be thrown");
    }


}
