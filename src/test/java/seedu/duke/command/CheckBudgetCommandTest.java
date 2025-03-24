package seedu.duke.command;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

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
        budgetManager = new BudgetManager();
        parser = new Parser();
    }

    @Test
    public void testExecute_validCategory() {
        try {
            budgetManager.setBudget("Food", 100.0);

            CheckBudgetCommand command = new CheckBudgetCommand("check-budget c/Monthly");
            command.execute(parser, budgetManager);

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
