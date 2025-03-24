package seedu.duke.command;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.BudgetManager;
import seedu.duke.Parser;
import seedu.duke.exception.InvalidInputException;

public class CheckBudgetCommandTest {

    private BudgetManager budgetManager;
    private Parser parser;

    @BeforeEach
    public void setUp() {
        budgetManager = new BudgetManager(); // Actual instance
        parser = new Parser(); // Actual instance
    }

    @Test
    public void testExecute_validCategory() {
        try {
            budgetManager.setBudget("Food", 100.0); // Set up a valid budget

            CheckBudgetCommand command = new CheckBudgetCommand("check-budget c/Monthly");
            command.execute(parser, budgetManager);

            // No assertion needed if no exception occurs (meaning success)
        } catch (InvalidInputException e) {
            fail("Unexpected InvalidInputException: " + e.getMessage());
        }
    }

    @Test
    public void testExecute_invalidCategory_throwsException() {
        CheckBudgetCommand command = new CheckBudgetCommand("check-budget UnknownCategory");

        assertThrows(InvalidInputException.class, () -> {
            command.execute(parser, budgetManager);
        }, "Expected InvalidInputException for invalid category");
    }
}
