package seedu.duke.command;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.BudgetManager;
import seedu.duke.Parser;
import seedu.duke.exception.InvalidInputException;

public class AlertCommandTest {

    private BudgetManager budgetManager;
    private Parser parser;

    @BeforeEach
    public void setUp() {
        budgetManager = new BudgetManager();
        parser = new Parser();
    }

    @Test
    public void testExecute_validAlert_setsBudgetAlert() {
        try {
            AlertCommand alertCommand = new AlertCommand("alert 100");
            alertCommand.execute(parser, budgetManager);

            assertEquals(100.0, budgetManager.getBudgetAlert().getAlertAmount(), "Budget alert should be set to 100");
        } catch (InvalidInputException e) {
            fail("Unexpected InvalidInputException: " + e.getMessage());
        }
    }

}
